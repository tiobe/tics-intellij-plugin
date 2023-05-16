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
            val annotationsPanel = ViolationsPanel(project)
            val annotationsPanelContent = contentManager.factory
                .createContent(
                    annotationsPanel,
                    "Annotations",
                    false
                )
            annotationsPanelContent.isCloseable = false
            contentManager.addDataProvider(annotationsPanel)
            contentManager.addContent(annotationsPanelContent)
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