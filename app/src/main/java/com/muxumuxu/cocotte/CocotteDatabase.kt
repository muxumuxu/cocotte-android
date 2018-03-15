package com.muxumuxu.cocotte

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.muxumuxu.cocotte.data.Food

// TODO: Make a better DB (Not only one table)
@Database(entities = [(Food::class)], version = 1)
abstract class CocotteDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object {

        private var INSTANCE: CocotteDatabase? = null

        fun getInstance(context: Context): CocotteDatabase =
                INSTANCE ?: synchronized(CocotteDatabase::class) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        CocotteDatabase::class.java, "Cocotte.db")
                        .build()
    }
}