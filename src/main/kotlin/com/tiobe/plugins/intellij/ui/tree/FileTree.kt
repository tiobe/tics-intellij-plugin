package com.tiobe.plugins.intellij.ui.tree

import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.setEmptyState
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.EditSourceOnDoubleClickHandler
import com.intellij.util.EditSourceOnEnterKeyHandler
import com.tiobe.plugins.intellij.ui.nodes.ViolationNode
import com.tiobe.plugins.intellij.ui.panels.InformationPanel
import javax.swing.tree.TreeModel

class FileTree(private val project: Project, model: TreeModel, emptyState: String) : Tree(model), DataProvider {

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
            navigateToFile()?.navigate(false)
        }
        EditSourceOnEnterKeyHandler.install(this) {
            navigateToFile()?.navigate(false)
        }
    }

    private fun navigateToFile(): OpenFileDescriptor? {
        val node = selectionPath?.lastPathComponent
        if (node is ViolationNode) {
            return OpenFileDescriptor(project, node.file, node.violation.line - 1, -1)
        }
        return null
    }

    override fun getData(dataId: String): Any? {
        return if (CommonDataKeys.VIRTUAL_FILE.`is`(dataId) || CommonDataKeys.NAVIGATABLE.`is`(dataId)) {
            navigateToFile()
        } else {
            null
        }
    }
}