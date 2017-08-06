package com.muxumuxu.cocotte

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.muxumuxu.cocotte.data.Food

class FoodActivity : AppCompatActivity() {
    companion object {
        val FOOD_PARAM = "food"
    }

    var food: Food? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        this.food = intent.getParcelableExtra(FOOD_PARAM)

        title = food!!.name
    }
}