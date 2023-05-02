package com.tiobe.plugins.intellij.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.tiobe.plugins.intellij.analyzer.TicsRunCommand

class CancelAnalysis : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TicsRunCommand.getInstance().stop()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isVisible =
            TicsRunCommand.getInstance().isRunning() && !TicsRunCommand.getInstance().isStopping()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}