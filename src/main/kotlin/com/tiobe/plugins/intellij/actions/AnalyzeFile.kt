package com.tiobe.plugins.intellij.actions

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

open class AnalyzeFile : AnalyzeAction() {
    override fun getTicsCommand(file: VirtualFile?, project: Project?): GeneralCommandLine {
        val command = GeneralCommandLine(TICS_COMMAND)
        command.addParameters(IDE_PARAMETER, IDE_NAME)

        project?.projectFile?.canonicalPath?.let {
            command.addParameters(PROJECT_FILE_PARAMETER, it)
        }

        file?.canonicalPath?.let {
            command.addParameter(it)
        }

        return command
    }

    override fun isFileRequired(): Boolean {
        return true
    }

    override fun isProjectRequired(): Boolean {
        return false
    }
}
