package com.tiobe.plugins.intellij.analyzer

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.openapi.util.Key

object RunCommand {
    private var handler: ProcessHandler? = null

    @Throws(ExecutionException::class)
    fun run(
        command: GeneralCommandLine,
        ignoreState: Boolean = false,
        callback: ((code: Int) -> Unit)? = null
    ): ProcessHandler {
        val handler = OSProcessHandler(command)
        val listener = ProcessListener(callback)
        if (!ignoreState) {
            handler.addProcessListener(listener)
            handler.startNotify()
            this.handler = handler
        }
        return handler
    }

    fun stop() {
        handler?.notifyTextAvailable("Process cancelled by user", Key<String>(""))
        handler?.destroyProcess()
    }
}