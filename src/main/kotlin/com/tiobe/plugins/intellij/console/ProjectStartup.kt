package com.tiobe.plugins.intellij.console

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class ProjectStartup : ProjectActivity {
    override suspend fun execute(project: Project) {
        TicsConsole.getInstance(project)
    }
}