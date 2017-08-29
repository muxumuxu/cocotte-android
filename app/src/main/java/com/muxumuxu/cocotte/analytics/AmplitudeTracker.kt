package com.muxumuxu.cocotte.analytics

import android.content.Context
import com.amplitude.api.Amplitude
import com.muxumuxu.cocotte.BuildConfig

class AmplitudeTracker(context: Context)
    : AbstractTracker() {

    companion object {
        val TOKEN = if (BuildConfig.DEBUG) "00d4356f153e0d7ccdac41869b9199bf" else "460ce79c6ad144a4f4ffa5549bebd674"
    }

    init {
        Amplitude.getInstance().initialize(context, AmplitudeTracker.TOKEN)
                .trackSessionEvents(true)
                .enableLocationListening()
    }

    override fun acceptEvent(event: Event): Boolean {
        return true
    }

    override fun postEvent(event: Event) {
        Amplitude.getInstance().logEvent(event.name, event.params)
    }
}