package com.muxumuxu.cocotte

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.muxumuxu.cocotte.analytics.Analytics
import com.muxumuxu.cocotte.analytics.Event
import com.muxumuxu.cocotte.data.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.completable.CompletableFromCallable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_food.*

class FoodActivity : AppCompatActivity() {
    companion object {
        const val FOOD_ID_PARAM = "food_id"
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
            if (food.risk.url != null) {
                risk.setTextColor(ContextCompat.getColor(this, R.color.link))
                risk.setOnClickListener {
                    CustomTabsUtils.openCustomTab(this, food.risk.url)
                }
            }
        }

        if (!food.info.isNullOrEmpty()) {
            information.text = food.info
        }

        danger.text = getFoodDanger(this, food)
        danger.setTextColor(ContextCompat.getColor(this,
                resources.getIdentifier(food.danger, "color", packageName)))
        danger.setCompoundDrawablesWithIntrinsicBounds(
                getDrawable(resources.getIdentifier(food.danger, "drawable", packageName))
                , null, null, null)

        favorize.isSelected = food.favorite
        favorize.setOnClickListener {
            food.favorite = !food.favorite
            Analytics.trackEvent(Event.favorize(food.name, food.favorite))

            CompletableFromCallable {
                CocotteDatabase.getInstance(this).foodDao().updateFood(food)
            }.subscribeOn(Schedulers.io()).subscribe()
        }

        share.setOnClickListener {
            Analytics.trackEvent(Event.share(food.name, category.name))

            shareFood(this, food)
        }
    }
}