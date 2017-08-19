package com.muxumuxu.cocotte.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(val id: Int, val name: String, val info: String?,
                @Json(name = "name_en") val nameEn: String?,
                @Json(name = "info_en") val infoEn: String?,
                val danger: String, val category: Category, val risk: Risk?, val url: String) : Parcelable