package com.tiobe.plugins.intellij.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.tiobe.plugins.intellij.console.TicsConsole

class ClearConsole : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.service<TicsConsole>()?.clear()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}