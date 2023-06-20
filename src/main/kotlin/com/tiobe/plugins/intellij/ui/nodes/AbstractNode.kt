package com.tiobe.plugins.intellij.ui.nodes


import com.tiobe.plugins.intellij.ui.tree.TreeCellRenderer
import javax.swing.tree.DefaultMutableTreeNode

abstract class AbstractNode : DefaultMutableTreeNode() {
    abstract fun render(renderer: TreeCellRenderer)
}