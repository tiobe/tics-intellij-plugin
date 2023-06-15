package com.tiobe.plugins.intellij.console

import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class TicsConsole(project: Project): Disposable {
    private var consoleView: ConsoleView

    init {
        consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).console
    }

    companion object {
        private val instances: MutableMap<Project, TicsConsole> = mutableMapOf()

        fun getInstance(project: Project): TicsConsole {
            return instances.computeIfAbsent(project) { TicsConsole(project) }
        }
    }

    fun attachToProcess(handler: ProcessHandler, firstLine: String? = null) {
        consoleView.clear()
        firstLine?.let {
            consoleView.print("$firstLine\n", ConsoleViewContentType.SYSTEM_OUTPUT)
        }
        consoleView.attachToProcess(handler)
    }

    fun clear() {
        consoleView.clear()
    }

    fun getComponent(): JComponent {
        return consoleView.component
    }

    override fun dispose() {
        consoleView.dispose()
    }
}