package com.muxumuxu.cocotte

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muxumuxu.cocotte.data.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private lateinit var adapter: FoodAdapter

    private lateinit var foodList: List<Food>

    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FoodAdapter("favorites", null)
        foods.adapter = adapter

         CocotteDatabase.getInstance(view.context).foodDao()
                .getFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    this.foodList = it
                    updateFoods(this.foodList)
                }.addTo(compositeDisposable)

        share.setOnClickListener {
            if (this.foodList.isEmpty()) shareApp(view.context) else shareFoods(view.context, this.foodList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    private fun updateFoods(foodList: List<Food>) {
        adapter.setFoods(foodList)

        val showEmpty = foodList.isEmpty()
        foods.visibility = if (!showEmpty) View.VISIBLE else View.GONE
        empty_view.visibility = if (showEmpty) View.VISIBLE else View.GONE
    }
}
