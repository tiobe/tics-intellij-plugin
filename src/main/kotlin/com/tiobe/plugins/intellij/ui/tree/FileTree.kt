package com.tiobe.plugins.intellij.ui.tree

import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.setEmptyState
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.EditSourceOnDoubleClickHandler
import com.intellij.util.EditSourceOnEnterKeyHandler
import com.tiobe.plugins.intellij.ui.nodes.ViolationNode
import com.tiobe.plugins.intellij.ui.panels.InformationPanel
import javax.swing.tree.TreeModel

class FileTree(private val project: Project, model: TreeModel, emptyState: String) : Tree(model) {

    init {

        isRootVisible = false
        setShowsRootHandles(true)
        setCellRenderer(TreeCellRenderer())
        setEmptyState(emptyState)

        addTreeSelectionListener {
            val node = selectionPath?.lastPathComponent
            if (node is ViolationNode) {
                InformationPanel.displayViolation(node.violation)
            } else {
                InformationPanel.noViolationToDisplay()
            }
        }

        EditSourceOnDoubleClickHandler.install(this) {
            navigateToFile()
        }
        EditSourceOnEnterKeyHandler.install(this) {
            navigateToFile()
        }
    }

    private fun navigateToFile() {
        val node = selectionPath?.lastPathComponent
        if (node is ViolationNode) {
            OpenFileDescriptor(project, node.file, node.violation.line - 1, -1).navigate(true)
        }
    }
}