package com.tiobe.plugins.intellij.console;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.execution.filters.Filter;

import javax.swing.*;
import java.awt.*;

public class TICSConsole {
    public static ConsoleView openConsole(Project project, String name, Filter... filters) {
        final ConsoleView consoleView =
                TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        for (Filter filter : filters) {
            consoleView.addMessageFilter(filter);
        }

        final JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.add(consoleView.getComponent(), BorderLayout.CENTER);

        final RunContentDescriptor runContentDescriptor = new RunContentDescriptor(consoleView, null, consolePanel, name) {
            public boolean isContentReuseProhibited() {
                return true; // prevents previously opened console from being reused
            }
        };
        ExecutionManager.getInstance(project).getContentManager().showRunContent(DefaultRunExecutor.getRunExecutorInstance(), runContentDescriptor);

        return consoleView;
    }
}
