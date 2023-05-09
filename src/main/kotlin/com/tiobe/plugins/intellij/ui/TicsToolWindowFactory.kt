package com.tiobe.plugins.intellij.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ToolWindowType
import com.intellij.ui.content.ContentManager
import com.tiobe.plugins.intellij.ui.panels.AnnotationsPanel
import com.tiobe.plugins.intellij.ui.panels.TicsConsolePanel

class TicsToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentManager = toolWindow.contentManager
        addCurrentFileTab(project, contentManager)
        addConsoleTab(project, contentManager)

        toolWindow.setType(ToolWindowType.DOCKED, null)
    }

    companion object {
        private fun addCurrentFileTab(project: Project, contentManager: ContentManager) {
            val currentFileContent = contentManager.factory
                .createContent(
                    AnnotationsPanel(project),
                    "Annotations",
                    false
                )
            currentFileContent.isCloseable = false
            contentManager.addContent(currentFileContent)
        }

        private fun addConsoleTab(project: Project, contentManager: ContentManager) {
            val logContent = contentManager.factory.createContent(
                TicsConsolePanel(project),
                "Console",
                false
            )
            logContent.isCloseable = false
            contentManager.addContent(logContent)
        }
    }
}