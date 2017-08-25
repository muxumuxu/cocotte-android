package com.muxumuxu.cocotte

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
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
            return getString(when (position) {
                0 -> R.string.categories
                1 -> R.string.favorites
                else -> 0
            })
        }
    }
}
