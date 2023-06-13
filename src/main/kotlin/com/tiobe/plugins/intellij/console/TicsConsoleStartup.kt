package com.tiobe.plugins.intellij.console

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class TicsConsoleStartup : StartupActivity {
    override fun runActivity(project: Project) {
        TicsConsole.setConsoleView(project)
    }
}