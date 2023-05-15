package com.tiobe.plugins.intellij.ui.tree

import com.intellij.openapi.vfs.VirtualFile
import com.tiobe.plugins.intellij.ui.nodes.AnnotationNode
import com.tiobe.plugins.intellij.ui.nodes.FileNode
import com.tiobe.plugins.intellij.ui.nodes.RootNode
import javax.swing.tree.DefaultTreeModel

class FileTreeModelBuilder {
    private val index = FileTreeIndex()
    private val rootNode = RootNode()
    private val model = DefaultTreeModel(rootNode)

    fun getModel(): DefaultTreeModel {
        model.setRoot(rootNode)
        return model
    }

    fun upsertFileWithAnnotations(file: VirtualFile, annotations: List<AnnotationNode.Annotation>) {
        if (!file.isValid || annotations.isEmpty()) {
            removeFile(file)
            return
        }

        var node = index.getFileNode(file)
        if (node == null) {
            node = FileNode(file)
            index.setFileNode(node)

            var insertIndex = rootNode.childCount
            for (i in 0 until insertIndex) {
                if ((rootNode.getChildAt(i) as FileNode).file.name.lowercase() > file.name.lowercase()) {
                    insertIndex = i
                    break
                }
            }
            rootNode.insert(node, insertIndex)
            model.reload(rootNode)
        }
        setAnnotations(node, annotations)
    }

    fun removeFile(file: VirtualFile) {
        index.getFileNode(file)?.let {
            index.remove(it.file)
            model.removeNodeFromParent(it)
            model.reload()
        }
    }

    private fun setAnnotations(node: FileNode, annotations: List<AnnotationNode.Annotation>) {
        node.removeAllChildren()
        annotations.forEach {
            node.add(AnnotationNode(it))
        }
        model.reload(node)
    }
}