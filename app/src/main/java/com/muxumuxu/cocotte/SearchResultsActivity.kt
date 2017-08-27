package com.muxumuxu.cocotte

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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

        if (!handleIntent(intent)) {
            finish()
            return
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FoodAdapter("search", intent.getStringExtra(SearchManager.QUERY))
        foods.adapter = adapter
        foods.setEmptyView(empty_view)

        suggest.setOnClickListener {
            Toast.makeText(this, "TODO", Toast.LENGTH_LONG).show()
            // TODO: Suggest food
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.all, R.id.empty, R.id.avoid, R.id.care -> {
                adapter.setFoods(when (item.itemId) {
                    R.id.empty -> foodList.filter { it.danger == "empty" }
                    R.id.care -> foodList.filter { it.danger == "care" }
                    R.id.avoid -> foodList.filter { it.danger == "avoid" }
                    else -> foodList
                })
                item.isChecked = true
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // FIXME: Do search in SQL
    private fun handleIntent(intent: Intent): Boolean {
        return if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            CocotteDatabase.getInstance(this).foodDao().getAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { foods ->
                        foodList = foods.filter { it.name.contains(query, ignoreCase = true) }
                        adapter.setFoods(foodList)
                    }
            true
        } else {
            false
        }
    }
}
