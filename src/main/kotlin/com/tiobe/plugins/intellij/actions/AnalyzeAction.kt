package com.tiobe.plugins.intellij.actions

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandler
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.tiobe.plugins.intellij.analyzer.TicsRunCommand
import com.tiobe.plugins.intellij.console.TicsConsole
import com.tiobe.plugins.intellij.errors.ErrorMessages
import com.tiobe.plugins.intellij.pane.TicsOptionPane.Companion.showErrorMessageDialog


abstract class AnalyzeAction : AnAction() {

    companion object {
        const val TICS_COMMAND: String = "TICS"
        const val IDE_PARAMETER: String = "-ide"
        const val IDE_NAME: String = "intellij"
        const val PROJECT_FILE_PARAMETER: String = "-projfile"
    }

    override fun actionPerformed(e: AnActionEvent) {
        val context: DataContext = e.dataContext

        val project: Project? = PlatformDataKeys.PROJECT.getData(context)
        if (isProjectRequired() && project == null) {
            showErrorMessageDialog(
                ErrorMessages.NO_ACTIVE_PROJECT
            )
            return
        }

        // try to get the file/folder from context, otherwise check if they can be retrieved from the opened editor
        val file: VirtualFile? = PlatformDataKeys.VIRTUAL_FILE.getData(context).let {
            it ?: project?.let { it1 ->
                FileEditorManager.getInstance(it1).selectedEditor?.file
            }
        }
        if (isFileRequired() && file == null) {
            showErrorMessageDialog(
                ErrorMessages.NO_ACTIVE_FILE
            )
            return
        }


        val handler: ProcessHandler
        try {
            FileDocumentManager.getInstance().saveAllDocuments()
            handler = TicsRunCommand.run(getTicsCommand(file, project))
        } catch (e: ExecutionException) {
            e.printStackTrace()
            showErrorMessageDialog(
                ErrorMessages.getExecutionErrorMessage(TICS_COMMAND, e.message)
            )
            return
        }

        val firstLine: String? = if (isFileRequired()) {
            file?.let {
                "Running analysis for ${it.name}"
            }
        } else {
            null
        }

        TicsConsole.attachToProcess(handler, firstLine)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isVisible =
            !TicsRunCommand.isRunning() && !TicsRunCommand.isStopping()
    }

    protected abstract fun getTicsCommand(file: VirtualFile?, project: Project?): GeneralCommandLine

    protected abstract fun isFileRequired(): Boolean

    protected abstract fun isProjectRequired(): Boolean

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}