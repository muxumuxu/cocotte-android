package com.muxumuxu.cocotte

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.muxumuxu.cocotte.data.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        CocotteDatabase.getInstance(this).foodDao()
                .getFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::fillList)
    }

    // TODO: Use something better than ViewSwitcher
    private fun fillList(foodList: List<Food>) {
        if (foodList.isNotEmpty()) {
            val adapter = FoodAdapter()
            adapter.setFoods(foodList)
            foods.adapter = adapter
            if (empty_switcher.currentView.id != R.id.foods) {
                empty_switcher.showNext()
            }
        } else {
            if (empty_switcher.currentView.id == R.id.foods) {
                empty_switcher.showNext()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}