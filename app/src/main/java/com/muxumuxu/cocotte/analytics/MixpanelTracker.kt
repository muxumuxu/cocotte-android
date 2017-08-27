package com.muxumuxu.cocotte.analytics

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.muxumuxu.cocotte.BuildConfig

class MixpanelTracker(context: Context)
    : AbstractTracker() {

    companion object {
        val TOKEN = if (BuildConfig.DEBUG) "273aa51ffb4fa55f8b8c55aa89f227b6" else "724399ebeb9b04fbbce6e249b615fb33"
    }

    private val tracker: MixpanelAPI

    init {
        tracker = MixpanelAPI.getInstance(context, MixpanelTracker.TOKEN)
    }

    override fun acceptEvent(event: Event): Boolean {
        return true
    }

    override fun postEvent(event: Event) {
        tracker.track(event.name, event.params)
    }
}