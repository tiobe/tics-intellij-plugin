package com.tiobe.plugins.intellij.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentManager
import com.tiobe.plugins.intellij.console.TicsConsole
import java.awt.BorderLayout
import javax.swing.JLabel
import javax.swing.JPanel

class TicsToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentManager = toolWindow.contentManager
        addCurrentFileTab(project, contentManager)
        addLogTab(project, contentManager)
    }

    companion object {
        private fun addCurrentFileTab(project: Project, contentManager: ContentManager) {
            val currentFilePanel = JLabel("Current File")
            val currentFileContent = contentManager.factory
                .createContent(
                    currentFilePanel,
                    "Current File",
                    false
                )
            currentFileContent.isCloseable = false
            contentManager.addContent(currentFileContent)
        }

        private fun addLogTab(project: Project, contentManager: ContentManager) {
            val logPanel = JPanel(BorderLayout())
            logPanel.add(TicsConsole.setConsoleView(project).component, BorderLayout.CENTER)
            val logContent = contentManager.factory.createContent(
                logPanel,
                "Log",
                false
            )
            logContent.isCloseable = false
            contentManager.addContent(logContent)
        }
    }
}