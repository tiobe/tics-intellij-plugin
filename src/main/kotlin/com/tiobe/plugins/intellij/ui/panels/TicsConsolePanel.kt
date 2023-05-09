package com.tiobe.plugins.intellij.ui

import com.intellij.openapi.project.Project
import com.intellij.tools.SimpleActionGroup
import com.tiobe.plugins.intellij.console.TicsConsole
import com.tiobe.plugins.intellij.utilities.TicsActions

class TicsConsolePanel(project: Project) : AbstractPanel(project) {
    init {
        addLogger()
    }

    override fun createActionGroup(): SimpleActionGroup {
        val actionGroup = super.createActionGroup()
        actionGroup.add(TicsActions.clearConsole)
        return actionGroup
    }

    private fun addLogger() {
        val consoleView = TicsConsole.setConsoleView(project)
        super.setContent(consoleView.component)
    }
}