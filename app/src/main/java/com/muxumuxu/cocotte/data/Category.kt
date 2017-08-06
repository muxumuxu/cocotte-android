package com.muxumuxu.cocotte.data

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Category(val id: Int, val name: String, @Json(name = "created_at") val createdAt: String,
                    @Json(name = "updated_at") val updatedAt: String, val order: Int,
                    val image: String, @Json(name = "name_en") val nameEn: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeInt(order)
        parcel.writeString(image)
        parcel.writeString(nameEn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}