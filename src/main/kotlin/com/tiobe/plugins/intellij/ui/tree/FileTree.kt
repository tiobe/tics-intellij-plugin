package com.tiobe.plugins.intellij.ui.tree

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.setEmptyState
import com.intellij.ui.treeStructure.Tree
import com.tiobe.plugins.intellij.ui.nodes.AbstractNode
import javax.swing.tree.TreeModel

class FileTree(project: Project, model: TreeModel, emptyState: String) : Tree(model) {
    private val project: Project

    init {
        this.project = project

        isRootVisible = false
        setShowsRootHandles(true)
        setCellRenderer(TreeCellRenderer())
        setEmptyState(emptyState)

        selectionModel.addTreeSelectionListener {
            if (it.source != null) {
                getSelectedNode()?.let {
                    /* some code here */
                }
            }
        }
    }

    private fun getSelectedNode(): AbstractNode? {
        return selectionPath.let {
            if (it == null) {
                null
            } else {
                it.lastPathComponent as AbstractNode
            }
        }
    }
}