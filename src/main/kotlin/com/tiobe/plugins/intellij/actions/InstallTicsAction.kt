package com.tiobe.plugins.intellij.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.tiobe.plugins.intellij.install.InstallTics


/**
 * Installs TICS on the host machine.
 */
class InstallTicsAction : AnAction() {
    private var project: Project? = null
    override fun actionPerformed(e: AnActionEvent) {
        project = PlatformDataKeys.PROJECT.getData(e.dataContext)

        InstallTics.install(project)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}