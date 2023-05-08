package com.tiobe.plugins.intellij.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.tiobe.plugins.intellij.analyzer.TicsRunCommand

class CancelAnalysis : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TicsRunCommand.stop()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isVisible =
            TicsRunCommand.isRunning() && !TicsRunCommand.isStopping()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}