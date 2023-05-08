package com.tiobe.plugins.intellij.ui

import com.intellij.ide.OccurenceNavigator
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel

class AbstractPanel constructor(project: Project) : SimpleToolWindowPanel(false, true), Disposable,
    OccurenceNavigator {

    override fun dispose() {
        TODO("Not yet implemented")
    }

    override fun hasNextOccurence(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPreviousOccurence(): Boolean {
        TODO("Not yet implemented")
    }

    override fun goNextOccurence(): OccurenceNavigator.OccurenceInfo {
        TODO("Not yet implemented")
    }

    override fun goPreviousOccurence(): OccurenceNavigator.OccurenceInfo {
        TODO("Not yet implemented")
    }

    override fun getNextOccurenceActionName(): String {
        TODO("Not yet implemented")
    }

    override fun getPreviousOccurenceActionName(): String {
        TODO("Not yet implemented")
    }
}