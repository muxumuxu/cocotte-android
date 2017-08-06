package com.muxumuxu.cocotte

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.muxumuxu.cocotte.data.Category
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {
    companion object {
        val CATEGORY_PARAM = "category"
    }

    var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        this.category = intent.getParcelableExtra(CATEGORY_PARAM)

        title = category!!.name
        val adapter = FoodAdapter()
        adapter.setFoods(Store.foods.filter { it.category.id == category!!.id })
        foods.adapter = adapter
        foods.layoutManager = LinearLayoutManager(this)
    }
}