package com.muxumuxu.cocotte

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.muxumuxu.cocotte.data.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search_results.*

class SearchResultsActivity : AppCompatActivity() {

    lateinit private var foodList: List<Food>

    lateinit private var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FoodAdapter()
        foods.adapter = adapter
        foods.setEmptyView(empty_view)

        suggest.setOnClickListener {
            Toast.makeText(this, "TODO", Toast.LENGTH_LONG).show()
            // TODO: Suggest food
        }

        // FIXME: Custom layout?
        (tabs as TabLayout).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                adapter.setFoods(when (tab.position) {
                    1 -> foodList.filter { it.danger == "empty" }
                    2 -> foodList.filter { it.danger == "care" }
                    3 -> foodList.filter { it.danger == "avoid" }
                    else -> foodList
                })
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    // FIXME: Do search in SQL
    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            CocotteDatabase.getInstance(this).foodDao().getAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { foods ->
                        foodList = foods.filter { it.name.contains(query, ignoreCase = true) }
                        adapter.setFoods(foodList)
                    }
        }
    }
}
