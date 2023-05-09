package com.tiobe.plugins.intellij.ui

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.tools.SimpleActionGroup
import com.tiobe.plugins.intellij.utilities.TicsActions
import javax.swing.Box

open class AbstractPanel(project: Project) : SimpleToolWindowPanel(false, true) {
    private val id = "TICS"
    val project: Project

    private lateinit var mainToolbar: ActionToolbar

    init {
        this.project = project

        addToolbar()
    }

    private fun addToolbar() {
        mainToolbar = ActionManager.getInstance().createActionToolbar(id, createActionGroup(), false)
        mainToolbar.targetComponent = FileEditorManager.getInstance(project).selectedEditor?.component
        mainToolbar.component.isVisible = true

        val toolBarBox = Box.createHorizontalBox()
        toolBarBox.add(mainToolbar.component)
        super.setToolbar(toolBarBox)
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