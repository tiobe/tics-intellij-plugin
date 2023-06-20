package com.tiobe.plugins.intellij.analysis

import com.intellij.util.messages.Topic

fun interface AnalysisListener {
    companion object {
        val TOPIC: Topic<AnalysisListener> = Topic.create("TICS Analysis Status", AnalysisListener::class.java)
    }

    fun change(analyzedFiles: List<String>)
}