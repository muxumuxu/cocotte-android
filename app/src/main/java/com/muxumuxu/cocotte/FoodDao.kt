package com.muxumuxu.cocotte

import android.arch.persistence.room.*
import com.muxumuxu.cocotte.data.Food
import io.reactivex.Flowable

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoods(foods: List<Food>)

    @Update
    fun updateFood(food: Food)

    @Query("SELECT * FROM foods")
    fun getAll(): Flowable<List<Food>>

    @Query("SELECT * FROM foods where favorite = 1")
    fun getFavorites(): Flowable<List<Food>>
}