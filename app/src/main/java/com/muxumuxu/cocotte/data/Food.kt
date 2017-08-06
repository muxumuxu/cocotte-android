package com.muxumuxu.cocotte.data

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Food(val id: Int, val name: String, val info: String?,
                @Json(name = "name_en") val nameEn: String?,
                @Json(name = "info_en") val infoEn: String?,
                val danger: String, val category: Category, val risk: Risk?, val url: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Category::class.java.classLoader),
            parcel.readParcelable(Risk::class.java.classLoader),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(info)
        parcel.writeString(nameEn)
        parcel.writeString(infoEn)
        parcel.writeString(danger)
        parcel.writeParcelable(category, flags)
        parcel.writeParcelable(risk, flags)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
}