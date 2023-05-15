package com.tiobe.plugins.intellij.ui.nodes

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.SimpleTextAttributes
import com.tiobe.plugins.intellij.ui.tree.TreeCellRenderer

class FileNode(file: VirtualFile) : AbstractNode() {
    val file: VirtualFile

    init {
        this.file = file
    }

    override fun render(renderer: TreeCellRenderer) {
        renderer.append(file.name)
        renderer.append(" ${file.path}", SimpleTextAttributes.GRAY_ATTRIBUTES)
        renderer.icon = file.fileType.icon
    }
}