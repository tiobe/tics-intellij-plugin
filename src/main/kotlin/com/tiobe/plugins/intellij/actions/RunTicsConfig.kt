package com.tiobe.plugins.intellij.actions

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.tiobe.plugins.intellij.analysis.ProcessRunner
import com.tiobe.plugins.intellij.console.TicsConsole
import com.tiobe.plugins.intellij.errors.ErrorMessages
import com.tiobe.plugins.intellij.pane.TicsOptionPane.Companion.showErrorMessageDialog

class RunTicsConfig : AnAction() {
    companion object {
        private val TICSCONFIG_COMMAND = GeneralCommandLine("TICSConfig")
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project: Project? = PlatformDataKeys.PROJECT.getData(e.dataContext)
        if (project == null) {
            showErrorMessageDialog(
                ErrorMessages.NO_ACTIVE_PROJECT
            )
            return
        }

        try {
            ProcessRunner.run(project, TICSCONFIG_COMMAND, true)
        } catch (e: ExecutionException) {
            e.printStackTrace()
            showErrorMessageDialog(
                ErrorMessages.getExecutionErrorMessage(TICSCONFIG_COMMAND.commandLineString, e.message)
            )
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = TicsConsole.isInitialized()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}