package com.tiobe.plugins.intellij.actions;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class AnalyzeProject extends AbstractAnalyzeAction {
    public GeneralCommandLine getTICSCommand(final VirtualFile file, final Project project) {
        final GeneralCommandLine cmd = new GeneralCommandLine(TICS_COMMAND);
        cmd.addParameters(IDE_PARAMETER, IDE_NAME);
        final VirtualFile projectFile = project == null ? null : project.getProjectFile();
        if (projectFile != null) {
            cmd.addParameters(PROJFILE_PARAMETER, projectFile.getCanonicalPath());
        }
        final VirtualFile baseDir = project == null ? null : project.getBaseDir();
        if (baseDir != null) {
            cmd.addParameters(baseDir.getCanonicalPath());
        }
        return cmd;
    }

    @Override
    public boolean isFileRequired() {
        return false;
    }

    @Override
    public boolean isProjectRequired() {
        return true;
    }
}
