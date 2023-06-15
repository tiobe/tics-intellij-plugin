package com.tiobe.plugins.intellij.ui.panels

import com.intellij.openapi.project.Project
import com.intellij.tools.SimpleActionGroup
import com.tiobe.plugins.intellij.console.TicsConsole
import com.tiobe.plugins.intellij.utilities.TicsActions

class ConsolePanel(project: Project) : AbstractPanel(project) {
    init {
        addConsole()
    }

    override fun createActionGroup(): SimpleActionGroup {
        val actionGroup = super.createActionGroup()
        actionGroup.add(TicsActions.clearConsole)
        return actionGroup
    }

    private fun addConsole() {
        val consoleView = TicsConsole.getInstance(project)
        super.setContent(consoleView.getComponent())
    }
}