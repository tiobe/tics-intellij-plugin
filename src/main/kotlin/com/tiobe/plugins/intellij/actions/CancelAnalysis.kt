package com.tiobe.plugins.intellij.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.tiobe.plugins.intellij.analysis.ProcessRunner
import com.tiobe.plugins.intellij.analysis.ProcessState

class CancelAnalysis : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        ProcessRunner.stop()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isVisible =
            ProcessState.isRunning() && !ProcessState.isStopping()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}