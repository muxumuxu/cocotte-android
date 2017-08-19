package com.muxumuxu.cocotte.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(val id: Int, val name: String, @Json(name = "created_at") val createdAt: String,
                    @Json(name = "updated_at") val updatedAt: String, val order: Int,
                    val image: String, @Json(name = "name_en") val nameEn: String?) : Parcelable