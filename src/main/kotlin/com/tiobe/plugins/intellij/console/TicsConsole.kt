package com.tiobe.plugins.intellij.console

import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.execution.ui.RunContentManager
import com.intellij.openapi.project.Project
import java.awt.BorderLayout
import javax.swing.JPanel

class TicsConsole {
    companion object {
        fun openConsole(project: Project, name: String, vararg filters: Filter): ConsoleView {
            val consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).console
            for (filter in filters) {
                consoleView.addMessageFilter(filter)
            }

            val consolePanel = JPanel(BorderLayout())
            consolePanel.add(consoleView.component, BorderLayout.CENTER)

            val runContentDescriptor: RunContentDescriptor = object : RunContentDescriptor(consoleView, null, consolePanel, name) {
                override fun isContentReuseProhibited(): Boolean {
                    return true // prevents previously opened console from being reused
                }
            }
            RunContentManager.getInstance(project).showRunContent(DefaultRunExecutor.getRunExecutorInstance(), runContentDescriptor)

            return consoleView
        }
    }
}