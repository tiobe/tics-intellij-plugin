package com.tiobe.plugins.intellij.analyzer

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.util.Key

enum class TicsProcessState {
    RUNNING,
    STOPPED,
    STOPPING
}

object TicsRunCommand {
    private var handler: ProcessHandler? = null
    private var state: TicsProcessState = TicsProcessState.STOPPED

    @Throws(ExecutionException::class)
    fun run(
        command: GeneralCommandLine,
        ignoreState: Boolean = false,
        callback: ((code: Int) -> Unit)? = null
    ): ProcessHandler {
        val handler: ProcessHandler = OSProcessHandler(command)
        val listener: ProcessListener = TicsProcessListener(callback)
        if (!ignoreState) {
            handler.addProcessListener(listener)
            handler.startNotify()
            this.handler = handler
        }
        return handler
    }

    private class TicsProcessListener(callback: ((code: Int) -> Unit)?) : ProcessListener {
        private val callbackFunction = callback

        override fun startNotified(event: ProcessEvent) {
            state = TicsProcessState.RUNNING
        }

        override fun processTerminated(event: ProcessEvent) {
            state = TicsProcessState.STOPPED
            ApplicationManager.getApplication().invokeLater {
                callbackFunction?.let { it(event.exitCode) }
            }
        }

        override fun processWillTerminate(event: ProcessEvent, willBeDestroyed: Boolean) {
            if (willBeDestroyed) {
                state = TicsProcessState.STOPPING
            }
        }

        override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
            /* nothing needed here */
        }

    }

    fun isRunning(): Boolean {
        return state == TicsProcessState.RUNNING || state == TicsProcessState.STOPPING
    }

    fun isStopping(): Boolean {
        return state == TicsProcessState.STOPPING
    }

    fun stop() {
        handler?.notifyTextAvailable("Process cancelled by user", Key<String>(""))
        handler?.destroyProcess()
    }
}