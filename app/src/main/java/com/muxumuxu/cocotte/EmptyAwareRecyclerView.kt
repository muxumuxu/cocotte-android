package com.muxumuxu.cocotte

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class EmptyAwareRecyclerView : RecyclerView {
    private var mEmptyView: View? = null

    private val mDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            updateEmptyView()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    /**
     * Designate a view as the empty view. When the backing adapter has no
     * data this view will be made visible and the recycler view hidden.
     *
     */
    fun setEmptyView(emptyView: View) {
        mEmptyView = emptyView
    }

    fun getEmptyView(): View? {
        return mEmptyView
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        getAdapter()?.unregisterAdapterDataObserver(mDataObserver)
        adapter?.registerAdapterDataObserver(mDataObserver)
        super.setAdapter(adapter)
        updateEmptyView()
    }

    fun updateEmptyView() {
        if (adapter != null) {
            val showEmptyView = adapter.itemCount == 0
            mEmptyView?.visibility = if (showEmptyView) View.VISIBLE else View.GONE
            visibility = if (showEmptyView) View.GONE else View.VISIBLE
        }
    }
}