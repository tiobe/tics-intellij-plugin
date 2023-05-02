package com.tiobe.plugins.intellij.actions

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.tiobe.plugins.intellij.analyzer.TicsRunCommand
import com.tiobe.plugins.intellij.errors.ErrorMessages
import com.tiobe.plugins.intellij.pane.TicsOptionPane.Companion.showErrorMessageDialog

class RunTicsConfig : AnAction() {
    companion object {
        private val TICSCONFIG_COMMAND = GeneralCommandLine("TICSConfig")
    }

    override fun actionPerformed(e: AnActionEvent) {
        try {
            TicsRunCommand.getInstance().run(TICSCONFIG_COMMAND, true)
        } catch (e: ExecutionException) {
            e.printStackTrace()
            showErrorMessageDialog(
                ErrorMessages.getExecutionErrorMessage(TICSCONFIG_COMMAND.commandLineString, e.message)
            )
        }
    }

}