package com.tiobe.plugins.intellij.ui.tree

import com.intellij.openapi.vfs.VirtualFile
import com.tiobe.plugins.intellij.ui.nodes.FileNode

class FileTreeIndex {
    private val fileNodes = HashMap<VirtualFile, FileNode>()

    fun getFileNode(file: VirtualFile): FileNode? {
        return fileNodes[file]
    }

    fun setFileNode(node: FileNode) {
        fileNodes[node.file] = node
    }

    fun remove(file: VirtualFile) {
        fileNodes.remove(file)
    }

    fun clear() {
        fileNodes.clear()
    }

    fun getAllFiles(): Set<VirtualFile> {
        return fileNodes.keys
    }
}