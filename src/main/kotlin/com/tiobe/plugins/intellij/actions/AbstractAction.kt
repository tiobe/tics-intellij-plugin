package com.tiobe.plugins.intellij.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.showOkCancelDialog
import com.tiobe.plugins.intellij.install.InstallTics

abstract class AbstractAction : AnAction() {

    /**
     * Checks if TICS is installed and if not, installs it (with user request).
     * @param project JetBrains defined variable for the project
     * @return if TICS is installed.
     */
    fun ticsInstalledWrapper(project: Project): Boolean {
        return if (!InstallTics.isTicsInstalled()) {
            val exitCode = showOkCancelDialog(
                "TICS Is Not Installed",
                "TICS is not installed on this machine, do you want to do this now?",
                "Install",
                "Cancel",
                AllIcons.General.QuestionDialog
            )
            if (exitCode == 0) {
                InstallTics.install(project)
            }
            false
        } else {
            true
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}