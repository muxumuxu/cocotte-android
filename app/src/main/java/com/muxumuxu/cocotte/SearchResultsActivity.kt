package com.muxumuxu.cocotte

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.muxumuxu.cocotte.data.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_search_results.*

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var foodList: List<Food>

    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        handleIntent(intent)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FoodAdapter("search", intent.getStringExtra(SearchManager.QUERY))
        foods.adapter = adapter

        suggest.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_EMAIL, contactEmail)
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.suggest_food_subject))
                    .setType("text/html")

            startActivity(intent)
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
                updateFoods(when (item.itemId) {
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

    private lateinit var disposable: Disposable

    // FIXME: Do search in SQL
    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            disposable = CocotteDatabase.getInstance(this).foodDao().getAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        this.foodList = it.filter { it.name.contains(query, ignoreCase = true) }
                        updateFoods(this.foodList)
                        disposable.dispose()
                    }
        }
    }

    private fun updateFoods(foodList: List<Food>) {
        adapter.setFoods(foodList)

        val showEmpty = foodList.isEmpty()
        foods.visibility = if (!showEmpty) View.VISIBLE else View.GONE
        empty_view.visibility = if (showEmpty) View.VISIBLE else View.GONE
    }
}
