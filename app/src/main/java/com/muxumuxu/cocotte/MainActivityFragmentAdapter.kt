package com.muxumuxu.cocotte

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainActivityFragmentAdapter(private val context: Context, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {

    companion object {
        private const val FRAGMENTS_NUM = 2
    }

    override fun getCount(): Int {
        return FRAGMENTS_NUM
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
            0 -> context.getString(R.string.categories)
            1 -> context.getString(R.string.favorites)
            else -> null
        }
    }
}