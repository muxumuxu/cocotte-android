package com.muxumuxu.cocotte.analytics

abstract class AbstractTracker {

    fun trackEvent(event: Event) {
        if (acceptEvent(event)) {
            postEvent(event)
        }
    }

    protected abstract fun acceptEvent(event: Event): Boolean

    protected abstract fun postEvent(event: Event)
}