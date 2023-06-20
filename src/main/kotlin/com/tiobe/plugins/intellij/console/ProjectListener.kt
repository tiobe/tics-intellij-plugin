package com.tiobe.plugins.intellij.console

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.util.Disposer

class ProjectListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        TicsConsole.getInstance(project)
    }

    override fun projectClosing(project: Project) {
        Disposer.dispose(TicsConsole.getInstance(project))
    }
}