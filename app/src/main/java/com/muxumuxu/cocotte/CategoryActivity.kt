package com.muxumuxu.cocotte

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.muxumuxu.cocotte.data.Category
import com.muxumuxu.cocotte.data.Food
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {
    companion object {
        val CATEGORY_PARAM = "category"
    }

    lateinit private var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        this.category = intent.getParcelableExtra(CATEGORY_PARAM)

        setSupportActionBar(toolbar)
        title = category.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = FoodAdapter()
        adapter.setFoods(this.getFoods())
        foods.adapter = adapter
        foods.layoutManager = LinearLayoutManager(this)

        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                adapter.setFoods(when (tab.position) {
                    1 -> getFoods().filter { it.danger == "empty" }
                    2 -> getFoods().filter { it.danger == "care" }
                    3 -> getFoods().filter { it.danger == "avoid" }
                    else -> getFoods()
                })
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
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

    private fun getFoods(): List<Food> {
        return Store.foods.filter { it.category.id == category.id }
    }
}