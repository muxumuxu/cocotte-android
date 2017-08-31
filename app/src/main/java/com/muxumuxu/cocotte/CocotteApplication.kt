package com.muxumuxu.cocotte

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.muxumuxu.cocotte.analytics.AmplitudeTracker
import com.muxumuxu.cocotte.analytics.Analytics
import com.muxumuxu.cocotte.analytics.MixpanelTracker
import io.fabric.sdk.android.Fabric

class CocotteApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }

        Analytics.init(AmplitudeTracker(this), MixpanelTracker(this))
    }
}