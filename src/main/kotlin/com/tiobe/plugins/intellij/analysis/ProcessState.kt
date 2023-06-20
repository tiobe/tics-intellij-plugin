package com.tiobe.plugins.intellij.analysis

object ProcessState {
    var state = State.STOPPED

    enum class State { RUNNING, STOPPED, STOPPING }

    fun isRunning(): Boolean {
        return state == State.RUNNING || state == State.STOPPING
    }

    fun isStopping(): Boolean {
        return state == State.STOPPING
    }
}