package com.muxumuxu.cocotte

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.muxumuxu.cocotte.data.Food
import kotlinx.android.synthetic.main.activity_food.*

class FoodActivity : AppCompatActivity() {
    companion object {
        val FOOD_PARAM = "food"
    }

    lateinit private var food: Food

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        this.food = intent.getParcelableExtra(FOOD_PARAM)

        title = food.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bindView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindView() {
        val category = food.category

        cover.setImageResource(resources.getIdentifier(category.image, "drawable", packageName))
        cover.background.setColorFilter(getCategoryColor(category.order - 1), PorterDuff.Mode.SRC_ATOP)

        food_title.text = food.name

        if (food.risk != null) {
            risk.text = food.risk?.name
        }

        if (!food.info.isNullOrEmpty()) {
            information.text = food.info
        }

        danger.text = getString(resources.getIdentifier(food.danger, "string", packageName))
        danger.setCompoundDrawablesWithIntrinsicBounds(getDrawable(resources.getIdentifier(food.danger, "drawable", packageName)), null, null, null)
    }
}