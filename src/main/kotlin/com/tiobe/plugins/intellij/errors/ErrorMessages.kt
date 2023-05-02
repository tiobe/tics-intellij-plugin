package com.tiobe.plugins.intellij.errors

import java.util.*

class ErrorMessages {
    companion object {
        const val NO_ACTIVE_FILE = "Could not determine active file. Please make sure the editor is focused on a file."
        const val NO_ACTIVE_PROJECT =
            "Could not determine active project. Please make sure the file you're analyzing is part of a project."
        const val NO_AUTH_FILE = "Incorrect of missing TICS authentication token file. See:"
        const val TICS_INSTALL_FAILED =
            "The installation or TICS failed, check the output for more information."

        private const val EXECUTION_ERROR =
            "There was a problem launching %1\$s. Please ensure %1\$s is added to the PATH environment variable.\n\nThe full error was: %2\$s"

        fun getExecutionErrorMessage(command: String?, fullError: String?): String {
            return String.format(EXECUTION_ERROR, command, Optional.ofNullable(fullError).orElse(""))
        }
    }
}