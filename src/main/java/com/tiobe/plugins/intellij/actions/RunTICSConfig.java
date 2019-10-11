package com.tiobe.plugins.intellij.actions;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.tiobe.plugins.intellij.analyzer.TICSAnalyzer;
import com.tiobe.plugins.intellij.errors.ErrorMessages;

import javax.swing.JOptionPane;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class RunTICSConfig extends com.intellij.openapi.actionSystem.AnAction {
    private static final GeneralCommandLine TICSCONFIG_COMMAND
            = new GeneralCommandLine("TICSConfig");

    @Override
    public void actionPerformed(final AnActionEvent event) {
        try {
            TICSAnalyzer.getInstance().run(TICSCONFIG_COMMAND, true);
        } catch (final ExecutionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    ErrorMessages.getExecutionErrorMessage(TICSCONFIG_COMMAND.getCommandLineString(), e.getMessage()),
                    ErrorMessages.getExecutionErrorMessageShort(TICSCONFIG_COMMAND.getCommandLineString()), ERROR_MESSAGE);
        }
    }
}
