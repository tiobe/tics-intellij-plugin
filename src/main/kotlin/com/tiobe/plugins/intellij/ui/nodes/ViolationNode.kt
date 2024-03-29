package com.tiobe.plugins.intellij.ui.nodes

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.SimpleTextAttributes
import com.tiobe.plugins.intellij.ui.tree.TreeCellRenderer
import com.tiobe.plugins.intellij.ui.utils.TextAttributes
import com.tiobe.plugins.intellij.utilities.Violation

class ViolationNode(val file: VirtualFile, val violation: Violation) : AbstractNode() {

    init {
        allowsChildren = false
    }

    override fun render(renderer: TreeCellRenderer) {
        renderer.append("(${violation.line})", SimpleTextAttributes.GRAYED_ATTRIBUTES)
        violation.new?.let {
            if (it.value) {
                renderer.append(" ")
                renderer.append("NEW", TextAttributes.ORANGE_BOLD_ATTRIBUTES)
            }
        }
        renderer.append(" ")
        renderer.append(if (violation.message.isNullOrEmpty()) violation.synopsis else violation.message)
    }
}