package com.muxumuxu.cocotte

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

inline fun <reified T : View> ViewGroup.inflate(
        @LayoutRes layoutRes: Int,
        attachToRoot: Boolean = true
): T = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot) as T

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}