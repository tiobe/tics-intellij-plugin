package com.tiobe.plugins.intellij.ui.panels

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.application.invokeLater
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.tools.SimpleActionGroup
import com.tiobe.plugins.intellij.analysis.ProcessStateListener
import com.tiobe.plugins.intellij.utilities.TicsActions

open class AbstractPanel(project: Project) : SimpleToolWindowPanel(false, true) {
    private val id = "TICS"
    val project: Project

    private lateinit var mainToolbar: ActionToolbar

    init {
        this.project = project

        addToolbar()

        project.messageBus.connect().subscribe(
            ProcessStateListener.TOPIC,
            ProcessStateListener {
                invokeLater {
                    mainToolbar.updateActionsImmediately()
                }
            }
        )
    }

    private fun addToolbar() {
        mainToolbar = ActionManager.getInstance().createActionToolbar(id, createActionGroup(), false)
        mainToolbar.targetComponent = this
        mainToolbar.component.isVisible = true

        super.setToolbar(mainToolbar.component)
    }

    open fun createActionGroup(): SimpleActionGroup {
        val actionGroup = SimpleActionGroup()
        actionGroup.add(TicsActions.analyzeFile)
        actionGroup.add(TicsActions.analyzeProject)
        actionGroup.add(TicsActions.cancelAnalysis)
        actionGroup.add(TicsActions.runTicsConfig)
        return actionGroup
    }
}