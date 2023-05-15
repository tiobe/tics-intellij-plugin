package com.tiobe.plugins.intellij.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.tiobe.plugins.intellij.analyzer.ProcessState
import com.tiobe.plugins.intellij.analyzer.RunCommand

class CancelAnalysis : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        RunCommand.stop()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isVisible =
            ProcessState.isRunning() && !ProcessState.isStopping()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}