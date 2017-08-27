package com.muxumuxu.cocotte

import android.app.Application
import com.muxumuxu.cocotte.analytics.AmplitudeTracker
import com.muxumuxu.cocotte.analytics.Analytics
import com.muxumuxu.cocotte.analytics.MixpanelTracker

class CocotteAplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Analytics.init(AmplitudeTracker(this), MixpanelTracker(this))
    }
}