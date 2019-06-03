package org.pbreakers.mobile.getticket.util

class NetworkState private constructor(val status: Status, val message: String? = null) {
    enum class Status {
        RUNNING, LOADED, ERROR
    }

    companion object {
        val RUNNING = NetworkState(Status.RUNNING)
        val LOADED = NetworkState(Status.LOADED)
        fun error(message: String?) = NetworkState(Status.ERROR, message)
    }
}