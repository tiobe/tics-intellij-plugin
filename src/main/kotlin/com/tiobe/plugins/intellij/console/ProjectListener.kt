package com.tiobe.plugins.intellij.console

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class ProjectListener(private val project: Project) : Disposable {
    override fun dispose() {
        /* No code needed here */
    }

}