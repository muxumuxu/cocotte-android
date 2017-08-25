package com.muxumuxu.cocotte

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    lateinit private var adapter: FoodAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FoodAdapter()
        foods.adapter = adapter
        foods.setEmptyView(empty_view)

        CocotteDatabase.getInstance(context).foodDao()
                .getFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { foodList ->
                    adapter.setFoods(foodList)
                }
    }
}