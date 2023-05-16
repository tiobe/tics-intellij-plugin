package com.tiobe.plugins.intellij.analysis

import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.openapi.application.invokeLater
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key

class ProcessListener(private val project: Project, callback: ((code: Int) -> Unit)?) : ProcessListener {
    private val callbackFunction = callback
    private var analyzedFiles = mutableListOf<String>()

    override fun startNotified(event: ProcessEvent) {
        ProcessState.state = ProcessState.State.RUNNING
        updateStatusListenerBus()
    }

    override fun processTerminated(event: ProcessEvent) {
        ProcessState.state = ProcessState.State.STOPPED
        updateStatusListenerBus()

        if (event.exitCode == 0) {
            updateAnalysisListenerBus()
        }

        invokeLater {
            callbackFunction?.let { it(event.exitCode) }
        }
    }

    override fun processWillTerminate(event: ProcessEvent, willBeDestroyed: Boolean) {
        if (willBeDestroyed) {
            ProcessState.state = ProcessState.State.STOPPING
            updateStatusListenerBus()
        }
    }

    override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
        // retrieve the analyzed files
        val regex = Regex("\\[INFO 30\\d{2}] Analyzing (?:\\d*/\\d* )*(.*)")
        regex.find(event.text)?.let {
            analyzedFiles.add(it.groupValues[1])
        }
    }

    private fun updateStatusListenerBus() {
        project.messageBus.syncPublisher(ProcessStateListener.TOPIC).change(ProcessState.state)
    }

    private fun updateAnalysisListenerBus() {
        project.messageBus.syncPublisher(AnalysisListener.TOPIC).change(analyzedFiles)
    }
}