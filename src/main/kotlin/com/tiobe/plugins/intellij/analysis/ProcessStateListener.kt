package com.tiobe.plugins.intellij.analysis

import com.intellij.util.messages.Topic

fun interface ProcessStateListener {
    companion object {
        val TOPIC: Topic<ProcessStateListener> = Topic.create("TICS Process Status", ProcessStateListener::class.java)
    }

    fun change(state: ProcessState.State)
}