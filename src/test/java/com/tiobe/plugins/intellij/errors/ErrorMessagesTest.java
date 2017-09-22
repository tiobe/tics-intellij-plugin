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
    final static private String TICS = "TICS";
    final static private String TICSQSERVER = "TICSQServer";
    final static private String TICSCONFIG = "TICSConfig";
    final static private String ERROR = "An error occurred.";

    final private String command;

    public ErrorMessagesTest(String command) {
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
        final String expected = "There was a problem launching " + command + ". Please ensure " + command + " is add to the PATH " +
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