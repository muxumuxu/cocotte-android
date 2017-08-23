package com.muxumuxu.cocotte

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
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

        getFoods().subscribe({ foodList ->
            adapter.setFoods(foodList)
        })

        setSupportActionBar(toolbar)
        title = category.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // TODO: Custom layout?
        (tabs as TabLayout).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                getFoods().subscribe({ foodList ->
                    adapter.setFoods(when (tab.position) {
                        1 -> foodList.filter { it.danger == "empty" }
                        2 -> foodList.filter { it.danger == "care" }
                        3 -> foodList.filter { it.danger == "avoid" }
                        else -> foodList
                    })
                })
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
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

    private fun getFoods(): Flowable<List<Food>> {
        return CocotteDatabase.getInstance(this).foodDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.filter { it.category.id == category.id } }
    }
}