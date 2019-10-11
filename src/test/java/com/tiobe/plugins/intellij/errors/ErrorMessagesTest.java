package com.tiobe.plugins.intellij.errors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static com.tiobe.plugins.intellij.errors.ErrorMessages.getExecutionErrorMessage;
import static com.tiobe.plugins.intellij.errors.ErrorMessages.getExecutionErrorMessageShort;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ErrorMessagesTest {
    private static final String TICS = "TICS";
    private static final String TICSQSERVER = "TICSQServer";
    private static final String TICSCONFIG = "TICSConfig";
    private static final String ERROR = "An error occurred.";

    private final String command;

    public ErrorMessagesTest(final String command) {
        this.command = command;
    }

    @Parameterized.Parameters
    public static Iterable<?> data() {
        return Arrays.asList(TICS, TICSQSERVER, TICSCONFIG);
    }

    @Test
    public void getExecutionErrorMessageTest() {
        final String fullError = ERROR;
        final String actual = getExecutionErrorMessage(command, fullError);
        final String expected = "There was a problem launching " + command + ". Please ensure " + command + " is added to the PATH " +
                "environment variable.\n\nThe full error was: " + fullError;
        assertEquals(expected, actual);
    }

    @Test
    public void getExecutionErrorMessageShortTest() {
        final String actual = getExecutionErrorMessageShort(command);
        final String expected = "An error occurred while executing " + command;
        assertEquals(expected, actual);
    }
}
