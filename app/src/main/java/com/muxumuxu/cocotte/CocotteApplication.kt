package com.muxumuxu.cocotte

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.muxumuxu.cocotte.analytics.AmplitudeTracker
import com.muxumuxu.cocotte.analytics.Analytics
import com.muxumuxu.cocotte.analytics.MixpanelTracker
import com.muxumuxu.cocotte.network.Endpoint
import io.fabric.sdk.android.Fabric
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CocotteApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }

        Analytics.init(AmplitudeTracker(this), MixpanelTracker(this))

        loadData()
    }

    /*
        Load data once from server when application first opened.
        Observables are waiting on the DB downfall so we just have to populate it (or remplace it)
        Retries every 5 sec if error, cannot fail
     */
    private fun loadData() {
        Endpoint.getInstance().fetchFoods()
                .subscribeOn(Schedulers.io())
                .retryWhen { it.flatMap { Flowable.timer(5, TimeUnit.SECONDS) } }
                .subscribe(CocotteDatabase.getInstance(this).foodDao()::insertFoods)
    }
}