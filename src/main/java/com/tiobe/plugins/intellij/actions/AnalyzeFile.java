package com.tiobe.plugins.intellij.actions;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class AnalyzeFile extends AbstractAnalyzeAction {
    public GeneralCommandLine getTICSCommand(VirtualFile file, Project project) {
        GeneralCommandLine cmd = new GeneralCommandLine(TICS_COMMAND);
        cmd.addParameters(IDE_PARAMETER, IDE_NAME);
        final VirtualFile projectFile = project == null ? null : project.getProjectFile();
        if (projectFile != null) {
            cmd.addParameters(PROJFILE_PARAMETER, projectFile.getCanonicalPath());
        }
        if (file != null && file.getCanonicalPath() != null) {
            cmd.addParameter(file.getCanonicalPath());
        }
        return cmd;
    }

    @Override
    public boolean isFileRequired() {
        return true;
    }

    @Override
    public boolean isProjectRequired() {
        return false;
    }
}
