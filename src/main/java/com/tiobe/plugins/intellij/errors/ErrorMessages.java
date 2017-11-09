package com.tiobe.plugins.intellij.errors;

public class ErrorMessages {
    final public static String NO_ACTIVE_FILE = "Could not determine active file. Please make sure the editor is focused on a file.";
    final public static String NO_ACTIVE_FILE_SHORT = "Could not determine active file";

    final public static String NO_ACTIVE_PROJECT = "Could not determine active project. Please make sure the file you're analyzing is part of a project.";
    final public static String NO_ACTIVE_PROJECT_SHORT = "Could not determine active project";

    final private static String EXECUTION_ERROR = "There was a problem launching %1$s. Please ensure %1$s is added to the PATH environment variable.\n\nThe full error was: %2$s";
    final private static String EXECUTION_ERROR_SHORT = "An error occurred while executing %s";

    public static String getExecutionErrorMessage(String command, String fullError) {
        return String.format(EXECUTION_ERROR, command, fullError);
    }

    public static String getExecutionErrorMessageShort(String command) {
        return String.format(EXECUTION_ERROR_SHORT, command);
    }
}
