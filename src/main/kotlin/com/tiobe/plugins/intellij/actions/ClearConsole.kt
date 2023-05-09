package com.tiobe.plugins.intellij.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.tiobe.plugins.intellij.console.TicsConsole

class ClearConsole : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TicsConsole.clear()
    }
}