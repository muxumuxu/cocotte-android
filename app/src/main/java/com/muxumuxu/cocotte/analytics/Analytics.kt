package com.muxumuxu.cocotte.analytics

object Analytics {

    private lateinit var trackers: Array<out AbstractTracker>

    fun trackEvent(event: Event) {
        trackers.forEach { it.trackEvent(event) }
    }

    fun init(vararg trackers: AbstractTracker) {
        this.trackers = trackers
    }
}