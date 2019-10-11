package com.tiobe.plugins.intellij.errors;

import java.util.Optional;

/**
 * Error messages that are used within the TICS IntelliJ plugin.
 */
public class ErrorMessages {
    public static final String NO_ACTIVE_FILE = "Could not determine active file. Please make sure the editor is focused on a file.";
    public static final String NO_ACTIVE_FILE_SHORT = "Could not determine active file";

    public static final String NO_ACTIVE_PROJECT = "Could not determine active project. Please make sure the file you're analyzing is part of a project.";
    public static final String NO_ACTIVE_PROJECT_SHORT = "Could not determine active project";

    private static final String EXECUTION_ERROR = "There was a problem launching %1$s. Please ensure %1$s is added to the PATH environment variable.\n\nThe full error was: %2$s";
    private static final String EXECUTION_ERROR_SHORT = "An error occurred while executing %s";

    public static String getExecutionErrorMessage(final String command, final String fullError) {
        return String.format(EXECUTION_ERROR, command, Optional.ofNullable(fullError).orElse(""));
    }

    public static String getExecutionErrorMessageShort(final String command) {
        return String.format(EXECUTION_ERROR_SHORT, command);
    }
}
