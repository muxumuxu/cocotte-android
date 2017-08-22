package com.muxumuxu.cocotte.data

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

@Entity(tableName = "foods")
data class Food(@PrimaryKey val id: Int, val name: String, val info: String?,
                @Json(name = "name_en") val nameEn: String?,
                @Json(name = "info_en") val infoEn: String?,
                val danger: String, @Embedded(prefix = "cat") val category: Category,
                @Embedded(prefix = "risk") val risk: Risk?, val url: String,
                var favorite: Boolean = false) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Category::class.java.classLoader),
            parcel.readParcelable(Risk::class.java.classLoader),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

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
        parcel.writeByte(if (favorite) 1 else 0)
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