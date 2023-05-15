package com.tiobe.plugins.intellij.ui.nodes

import com.tiobe.plugins.intellij.ui.tree.TreeCellRenderer

class RootNode : AbstractNode() {
    override fun render(renderer: TreeCellRenderer) {
        renderer.append("No annotations to display")
    }
}