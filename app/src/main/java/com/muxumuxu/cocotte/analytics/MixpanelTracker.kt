package com.muxumuxu.cocotte.analytics

import android.content.Context
import com.google.android.gms.iid.InstanceID
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.muxumuxu.cocotte.BuildConfig
import com.muxumuxu.cocotte.R

// TODO: Lifecycle event to call #{tracker.flush}
class MixpanelTracker(context: Context)
    : AbstractTracker() {

    companion object {
        val TOKEN = if (BuildConfig.DEBUG) "273aa51ffb4fa55f8b8c55aa89f227b6" else "724399ebeb9b04fbbce6e249b615fb33"
    }

    private val tracker: MixpanelAPI

    init {
        tracker = MixpanelAPI.getInstance(context, MixpanelTracker.TOKEN)
        val people = tracker.people

        people.identify(InstanceID.getInstance(context).id)
        people.initPushHandling(context.getString(R.string.gcm_sender_id))
    }

    override fun acceptEvent(event: Event): Boolean {
        return true
    }

    override fun postEvent(event: Event) {
        tracker.track(event.name, event.params)
    }
}