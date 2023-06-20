package com.tiobe.plugins.intellij.analysis

import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.openapi.application.invokeLater
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.tiobe.plugins.intellij.pane.TicsOptionPane.Companion.showErrorMessageDialog

class ProcessListener(private val project: Project, callback: ((code: Int) -> Unit)?) : ProcessListener {
    private val callbackFunction = callback
    private var analyzedFiles = mutableListOf<String>()
    private var canceledByUser = false

    override fun startNotified(event: ProcessEvent) {
        ProcessState.state = ProcessState.State.RUNNING
        updateStatusListenerBus()
    }

    override fun processTerminated(event: ProcessEvent) {
        ProcessState.state = ProcessState.State.STOPPED
        updateStatusListenerBus()

        // if the process is canceled by the user, nothing needs to happen
        if (!canceledByUser) {
            updateAnalysisListenerBus()

            if (event.exitCode != 0) {
                showErrorMessageDialog("TICS Analysis unsuccessful, please check the TICS Console for errors.")
            }

            invokeLater {
                callbackFunction?.let { it(event.exitCode) }
            }
        }
        canceledByUser = false
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

    fun setCanceledByUser() {
        canceledByUser = true
    }

    private fun updateStatusListenerBus() {
        project.messageBus.syncPublisher(ProcessStateListener.TOPIC).change(ProcessState.state)
    }

    private fun updateAnalysisListenerBus() {
        project.messageBus.syncPublisher(AnalysisListener.TOPIC).change(analyzedFiles)
    }
}