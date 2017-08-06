package com.muxumuxu.cocotte.data

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Risk(val id: Int, val name: String, val url: String?,
                @Json(name = "created_at") val createdAt: String,
                @Json(name = "updated_at") val updatedAt: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Risk> {
        override fun createFromParcel(parcel: Parcel): Risk {
            return Risk(parcel)
        }

        override fun newArray(size: Int): Array<Risk?> {
            return arrayOfNulls(size)
        }
    }
}
