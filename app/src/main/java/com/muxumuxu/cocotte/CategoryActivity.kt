package com.muxumuxu.cocotte

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.muxumuxu.cocotte.data.Category
import com.muxumuxu.cocotte.data.Food
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {
    companion object {
        val CATEGORY_PARAM = "category"
    }

    lateinit private var category: Category

    lateinit private var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        this.category = intent.getParcelableExtra(CATEGORY_PARAM)

        adapter = FoodAdapter()
        foods.adapter = adapter

        getFoods().subscribe { foodList ->
            adapter.setFoods(foodList)
        }

        title = category.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.all, R.id.empty, R.id.avoid, R.id.care -> {
                getFoods().subscribe { foodList ->
                    adapter.setFoods(when (item.itemId) {
                        R.id.empty -> foodList.filter { it.danger == "empty" }
                        R.id.care -> foodList.filter { it.danger == "care" }
                        R.id.avoid -> foodList.filter { it.danger == "avoid" }
                        else -> foodList
                    })
                }
                item.isChecked = true
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getFoods(): Flowable<List<Food>> {
        return CocotteDatabase.getInstance(this).foodDao()
                .getFoodFromCategory(category.id)
                .observeOn(AndroidSchedulers.mainThread())
    }
}