package com.tiobe.plugins.intellij.analyzer

import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.util.Key

class ProcessListener(callback: ((code: Int) -> Unit)?) : ProcessListener {
    private val callbackFunction = callback

    override fun startNotified(event: ProcessEvent) {
        ProcessState.state = ProcessState.State.RUNNING
    }

    override fun processTerminated(event: ProcessEvent) {
        ProcessState.state = ProcessState.State.STOPPED
        ApplicationManager.getApplication().invokeLater {
            callbackFunction?.let { it(event.exitCode) }
        }
    }

    override fun processWillTerminate(event: ProcessEvent, willBeDestroyed: Boolean) {
        if (willBeDestroyed) {
            ProcessState.state = ProcessState.State.STOPPING
        }
    }

    override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
        /* nothing needed here */
    }
}