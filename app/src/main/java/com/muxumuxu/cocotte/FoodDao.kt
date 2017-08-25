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

    @Query("SELECT * FROM foods WHERE catid = :id")
    fun getFoodFromCategory(id: Int): Flowable<List<Food>>

    @Query("SELECT * FROM foods WHERE favorite = 1")
    fun getFavorites(): Flowable<List<Food>>

    @Query("SELECT * FROM foods WHERE id = :id")
    fun getFood(id: Int): Flowable<Food>
}