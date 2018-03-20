package com.muxumuxu.cocotte


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoriesAdapter()
        categories.adapter = adapter
        categories.layoutManager = GridLayoutManager(context, 2)

        CocotteDatabase.getInstance(view.context).foodDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (!it.isEmpty()) {
                        adapter.setCategories(it.map { it.category }.distinct().sortedBy { it.order })
                        progress.visibility = View.GONE
                        categories.visibility = View.VISIBLE
                    }
                }.addTo(compositeDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
