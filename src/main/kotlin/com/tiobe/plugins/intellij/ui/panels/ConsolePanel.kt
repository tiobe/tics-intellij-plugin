package com.tiobe.plugins.intellij.ui.panels

import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.tiobe.plugins.intellij.console.TicsConsole
import com.tiobe.plugins.intellij.utilities.TicsActions

class ConsolePanel(project: Project) : AbstractPanel(project) {
    init {
        addConsole()
    }

    override fun createActionGroup(): DefaultActionGroup {
        val actionGroup = super.createActionGroup()
        actionGroup.add(TicsActions.clearConsole)
        return actionGroup
    }

    private fun addConsole() {
        super.setContent(project.service<TicsConsole>().getComponent())
    }
}