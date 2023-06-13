package com.tiobe.plugins.intellij.console

import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.project.Project

object TicsConsole {
    private lateinit var consoleView: ConsoleView

    fun setConsoleView(project: Project): ConsoleView {
        if (!isInitialized()) {
            consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).console
        }
        return consoleView
    }

    fun attachToProcess(handler: ProcessHandler, firstLine: String? = null) {
        if (isInitialized()) {
            consoleView.clear()
            firstLine?.let {
                consoleView.print("$firstLine\n", ConsoleViewContentType.SYSTEM_OUTPUT)
            }
            consoleView.attachToProcess(handler)
        } else {
            throw Error("Console has not been registered yet.")
        }
    }

    fun clear() {
        if (isInitialized()) {
            consoleView.clear()
        } else {
            throw Error("Console has not been registered yet.")
        }
    }

    fun isInitialized(): Boolean {
        return this::consoleView.isInitialized
    }
}