package com.tiobe.plugins.intellij.actions

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.tiobe.plugins.intellij.analysis.ProcessRunner
import com.tiobe.plugins.intellij.errors.ErrorMessages
import com.tiobe.plugins.intellij.pane.TicsOptionPane.Companion.showErrorMessageDialog

class LaunchTicsConfig : AbstractAction() {
    companion object {
        private val TICSCONFIG_COMMAND = GeneralCommandLine("TICSConfig")
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project: Project? = e.project
        if (project == null) {
            showErrorMessageDialog(
                ErrorMessages.NO_ACTIVE_PROJECT
            )
            return
        }

        if (ticsInstalledWrapper(project)) {
            launchTicsConfig(project)
        }
    }

    private fun launchTicsConfig(project: Project) {
        try {
            ProcessRunner.run(project, TICSCONFIG_COMMAND, true)
        } catch (e: ExecutionException) {
            e.printStackTrace()
            showErrorMessageDialog(
                ErrorMessages.getExecutionErrorMessage(TICSCONFIG_COMMAND.commandLineString, e.message)
            )
        }
    }
}