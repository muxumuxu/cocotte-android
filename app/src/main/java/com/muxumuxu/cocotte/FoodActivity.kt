package com.muxumuxu.cocotte

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.muxumuxu.cocotte.data.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.completable.CompletableFromCallable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_food.*

class FoodActivity : AppCompatActivity() {
    companion object {
        val FOOD_ID_PARAM = "food_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        CocotteDatabase.getInstance(this).foodDao()
                .getFood(intent.getIntExtra(FOOD_ID_PARAM, -1))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { food -> bindView(food) }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // TODO: Better sync?
    private fun bindView(food: Food) {
        val category = food.category

        title = food.name

        cover.setImageResource(resources.getIdentifier(category.image, "drawable", packageName))
        cover.background.setColorFilter(getCategoryColor(category.order - 1), PorterDuff.Mode.SRC_ATOP)

        food_title.text = food.name

        if (food.risk != null) {
            risk.text = food.risk.name
        }

        if (!food.info.isNullOrEmpty()) {
            information.text = food.info
        }

        danger.text = getString(resources.getIdentifier(food.danger, "string", packageName))
        danger.setCompoundDrawablesWithIntrinsicBounds(
                getDrawable(resources.getIdentifier(food.danger, "drawable", packageName))
                , null, null, null)

        favorize.isSelected = food.favorite
        favorize.setOnClickListener {
            food.favorite = !food.favorite
            CompletableFromCallable {
                CocotteDatabase.getInstance(this).foodDao().updateFood(food)
            }.subscribeOn(Schedulers.io()).subscribe()
        }
    }
}