package com.tiobe.plugins.intellij.actions;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tiobe.plugins.intellij.mock.MockProject;
import com.tiobe.plugins.intellij.mock.MockVirtualFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AnalyzeProjectTest {
    private final VirtualFile file;
    private final Project project;
    private final GeneralCommandLine expected;
    private AnalyzeProject instance;

    public AnalyzeProjectTest(VirtualFile file, Project project, GeneralCommandLine expected) {
        this.file = file;
        this.project = project;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Iterable<?> data() {
        return Arrays.asList(new Object[][]{
                {new MockVirtualFile("/tmp/test"), new MockProject("test", "/tmp/test"), new GeneralCommandLine("TICS", "-ide", "intellij", "-projfile", "/tmp/test/.idea/misc.xml", "/tmp/test")},
                {new MockVirtualFile("C:\\tmp\\test"), new MockProject("test", "C:\\tmp"), new GeneralCommandLine("TICS", "-ide", "intellij", "-projfile", "C:\\tmp/.idea/misc.xml", "C:\\tmp")}
        });
    }

    @Before
    public void setUp() throws Exception {
        this.instance = new AnalyzeProject();
    }

    @Test
    public void getTICSCommandTest() throws Exception {
        final GeneralCommandLine actual = this.instance.getTICSCommand(file, project);
        assertEquals(expected.getCommandLineString(), actual.getCommandLineString());
    }
}