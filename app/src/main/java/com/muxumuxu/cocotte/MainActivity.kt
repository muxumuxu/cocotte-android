package com.muxumuxu.cocotte

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.muxumuxu.cocotte.network.Endpoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val NUM_ITEMS = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        CocotteDatabase.getInstance(this).foodDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { foodList ->
                    if (foodList.isEmpty()) {
                        Endpoint.getInstance().fetchFoods()
                                .subscribeOn(Schedulers.io())
                                .subscribe(CocotteDatabase.getInstance(this).foodDao()::insertFoods)
                    }
                }

        setSupportActionBar(toolbar)

        pager.adapter = MainAdapter(supportFragmentManager)
        tabs.setupWithViewPager(pager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search_menu).actionView as SearchView)
                .setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.more_menu -> {
                startActivity(Intent(this, MoreActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MainAdapter(fragmentManager: FragmentManager)
        : FragmentPagerAdapter(fragmentManager) {

        override fun getCount(): Int {
            return NUM_ITEMS
        }

        override fun getItem(position: Int): Fragment? {
            return (when (position) {
                0 -> CategoriesFragment()
                1 -> FavoritesFragment()
                else -> null
            })
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> getString(R.string.categories)
                1 -> getString(R.string.favorites)
                else -> null
            }
        }
    }
}
