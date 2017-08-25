package com.muxumuxu.cocotte


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {

    lateinit private var adapter: CategoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CategoriesAdapter()
        categories.adapter = adapter
        categories.layoutManager = GridLayoutManager(context, 2)

        CocotteDatabase.getInstance(context).foodDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { foodList ->
                    adapter.setCategories(foodList.map { it.category }.distinct().sortedBy { it.order })
                }
    }
}
