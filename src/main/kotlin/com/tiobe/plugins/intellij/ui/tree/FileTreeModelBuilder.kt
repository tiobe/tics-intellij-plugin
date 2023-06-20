package com.tiobe.plugins.intellij.ui.tree

import com.intellij.openapi.vfs.VirtualFile
import com.tiobe.plugins.intellij.logger.logger
import com.tiobe.plugins.intellij.ui.nodes.FileNode
import com.tiobe.plugins.intellij.ui.nodes.RootNode
import com.tiobe.plugins.intellij.ui.nodes.ViolationNode
import com.tiobe.plugins.intellij.utilities.Violation
import javax.swing.tree.DefaultTreeModel

class FileTreeModelBuilder {
    private val rootNode = RootNode()
    private val model = DefaultTreeModel(rootNode)
    private val index = FileTreeIndex()

    fun getModel(): DefaultTreeModel {
        model.setRoot(rootNode)
        return model
    }

    fun upsertFileWithAnnotations(file: VirtualFile, violations: List<Violation>) {
        if (!file.isValid || violations.isEmpty()) {
            if(violations.isEmpty()) logger.info("No violations for file ${file.path}.")
            else logger.warn("File ${file.path} is invalid.")
            removeFile(file)
            return
        }

        var node = index.getFileNode(file)
        if (node == null) {
            logger.info("Creating new FileNode for ${file.path}.")
            node = FileNode(file)
            index.setFileNode(node)

            logger.info("Finding index to insert ${file.path} into.")
            var insertIndex = rootNode.childCount
            for (i in 0 until insertIndex) {
                if ((rootNode.getChildAt(i) as FileNode).file.name.lowercase() > file.name.lowercase()) {
                    insertIndex = i
                    break
                }
            }
            logger.info("Adding ${file.path} to tree.")
            rootNode.insert(node, insertIndex)
            model.reload()
        }
        setAnnotations(node, violations)
    }

    private fun removeFile(file: VirtualFile) {
        index.getFileNode(file)?.let {
            logger.info("Removing file ${file.path} from tree.")
            index.remove(it.file)
            model.removeNodeFromParent(it)
            model.reload()
        }
    }

    private fun setAnnotations(node: FileNode, violations: List<Violation>) {
        node.removeAllChildren()
        logger.info("Adding violations to FileNode of ${node.file.path}.")
        violations.forEach {
            node.add(ViolationNode(node.file, it))
        }
        model.reload()
    }
}