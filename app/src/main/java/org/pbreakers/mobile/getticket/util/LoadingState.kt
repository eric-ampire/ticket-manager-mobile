package org.pbreakers.mobile.getticket.util

class LoadingState private constructor(val status: Status, val message: String? = null) {
    enum class Status {
        RUNNING, LOADED, ERROR
    }

    companion object {
        val RUNNING = LoadingState(Status.RUNNING)
        val LOADED = LoadingState(Status.LOADED)
        fun error(message: String?) = LoadingState(Status.ERROR, message)
    }
}