package com.tiobe.plugins.intellij.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ToolWindowType
import com.intellij.ui.content.ContentManager
import com.tiobe.plugins.intellij.ui.panels.ConsolePanel
import com.tiobe.plugins.intellij.ui.panels.ViolationsPanel

class TicsToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentManager = toolWindow.contentManager
        addAnnotationsTab(project, contentManager)
        addConsoleTab(project, contentManager)

        toolWindow.setType(ToolWindowType.DOCKED, null)
    }

    companion object {
        private fun addAnnotationsTab(project: Project, contentManager: ContentManager) {
            val violationsPanel = ViolationsPanel(project)
            val violationsPanelContent = contentManager.factory
                .createContent(
                    violationsPanel,
                    "Violations",
                    false
                )
            violationsPanelContent.isCloseable = false
            contentManager.addDataProvider(violationsPanel)
            contentManager.addContent(violationsPanelContent)
        }

        private fun addConsoleTab(project: Project, contentManager: ContentManager) {
            val logContent = contentManager.factory.createContent(
                ConsolePanel(project),
                "Console",
                false
            )
            logContent.isCloseable = false
            contentManager.addContent(logContent)
        }
    }
}