package com.tiobe.plugins.intellij.actions;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tiobe.plugins.intellij.analyzer.TICSAnalyzer;
import com.tiobe.plugins.intellij.console.TICSConsole;
import com.tiobe.plugins.intellij.console.TICSOutputFilter;
import com.tiobe.plugins.intellij.errors.ErrorMessages;

import javax.swing.JOptionPane;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * {@link AnAction} that invokes TICS Client on a set of files or on a project.
 */
abstract class AbstractAnalyzeAction extends AnAction {
    static final String TICS_COMMAND = "TICS";
    static final String IDE_PARAMETER = "-ide";
    static final String IDE_NAME = "intellij";
    static final String PROJFILE_PARAMETER = "-projfile";

    @Override
    public final void actionPerformed(final AnActionEvent event) {
        final DataContext context = event.getDataContext();
        final VirtualFile file = DataKeys.VIRTUAL_FILE.getData(context);
        final Project project = DataKeys.PROJECT.getData(context);
        if (isFileRequired() && file == null) {
            JOptionPane.showMessageDialog(null,
                    ErrorMessages.NO_ACTIVE_FILE,
                    ErrorMessages.NO_ACTIVE_FILE_SHORT, ERROR_MESSAGE);
            return;
        }
        if (isProjectRequired() && project == null) {
            JOptionPane.showMessageDialog(null,
                    ErrorMessages.NO_ACTIVE_PROJECT,
                    ErrorMessages.NO_ACTIVE_PROJECT_SHORT, ERROR_MESSAGE);
            return;
        }
        final ProcessHandler handler;
        try {
            FileDocumentManager.getInstance().saveAllDocuments();
            handler = TICSAnalyzer.getInstance().run(getTICSCommand(file, project));
        } catch (final ExecutionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    ErrorMessages.getExecutionErrorMessage(TICS_COMMAND, e.getMessage()),
                    ErrorMessages.getExecutionErrorMessageShort(TICS_COMMAND), ERROR_MESSAGE);
            return;
        }
        final String name = getName(file, project);
        final Filter outputFilter = new TICSOutputFilter(project);
        final ConsoleView consoleView = TICSConsole.openConsole(project, String.format("TICS (%s)", name), outputFilter);
        consoleView.attachToProcess(handler);
    }

    @Override
    public final void update(final AnActionEvent event) {
        event.getPresentation().setEnabled(!TICSAnalyzer.getInstance().isRunning());
    }

    protected abstract GeneralCommandLine getTICSCommand(VirtualFile file, Project project);

    protected abstract boolean isFileRequired();
    protected abstract boolean isProjectRequired();

    private String getName(final VirtualFile file, final Project project) {
        if (file != null) {
            return file.getName();
        } else if (project != null) {
            return project.getName();
        } else {
            return "unknown";
        }
    }
}
