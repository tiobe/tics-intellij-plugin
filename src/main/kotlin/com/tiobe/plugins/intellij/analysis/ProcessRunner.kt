package com.tiobe.plugins.intellij.analysis

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key

object ProcessRunner {
    private var handler: ProcessHandler? = null
    private var listener: ProcessListener? = null

    @Throws(ExecutionException::class)
    fun run(
        project: Project,
        command: GeneralCommandLine,
        ignoreState: Boolean = false,
        callback: ((code: Int) -> Unit)? = null
    ): OSProcessHandler {
        val handler = OSProcessHandler(command)
        val listener = ProcessListener(project, callback)
        if (!ignoreState) {
            handler.addProcessListener(listener)
            handler.startNotify()
            this.listener = listener
            this.handler = handler
        }
        return handler
    }

    fun stop() {
        listener?.setCanceledByUser()
        handler?.notifyTextAvailable("Process cancelled by user", Key<String>(""))
        handler?.destroyProcess()
    }
}