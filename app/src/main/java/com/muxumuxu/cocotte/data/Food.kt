package com.muxumuxu.cocotte.data

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "foods")
data class Food(@PrimaryKey val id: Int, val name: String, val info: String?,
                val nameEn: String?, val infoEn: String?, val danger: String,
                @Embedded(prefix = "cat") val category: Category,
                @Embedded(prefix = "risk") val risk: Risk?, val url: String,
                var favorite: Boolean = false)