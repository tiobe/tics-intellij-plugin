package com.tiobe.plugins.intellij.ui.tree;

import com.intellij.ui.ColoredTreeCellRenderer
import com.tiobe.plugins.intellij.ui.nodes.AbstractNode
import javax.swing.JTree

class TreeCellRenderer : ColoredTreeCellRenderer() {
    override fun customizeCellRenderer(
        tree: JTree,
        value: Any?,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        hasFocus: Boolean
    ) {
        if (value is AbstractNode) {
            value.render(this)
        }
    }
}