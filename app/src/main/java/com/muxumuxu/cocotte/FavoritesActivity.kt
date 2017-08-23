package com.muxumuxu.cocotte

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.muxumuxu.cocotte.data.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    lateinit private var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FoodAdapter()
        foods.adapter = adapter
        foods.setEmptyView(empty_view)

        CocotteDatabase.getInstance(this).foodDao()
                .getFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { foodList ->
                    adapter.setFoods(foodList)
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