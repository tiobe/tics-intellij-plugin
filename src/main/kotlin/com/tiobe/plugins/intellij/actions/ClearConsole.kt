package com.tiobe.plugins.intellij.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.tiobe.plugins.intellij.console.TicsConsole

class ClearConsole : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TicsConsole.clear()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = TicsConsole.isInitialized()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}