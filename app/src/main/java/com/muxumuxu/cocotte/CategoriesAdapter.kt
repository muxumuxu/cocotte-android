package com.muxumuxu.cocotte

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.muxumuxu.cocotte.analytics.Analytics
import com.muxumuxu.cocotte.analytics.Event
import com.muxumuxu.cocotte.data.Category
import kotlinx.android.synthetic.main.category_item.view.*
import java.util.*

class CategoriesAdapter : RecyclerView.Adapter<CategoryViewHolder>() {

    private var categories: List<Category>

    init {
        categories = ArrayList()
    }

    fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }
}

class CategoryViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.category_item)) {

    fun bind(item: Category) = with(itemView) {
        cover.setImageResource(context.resources.getIdentifier(item.image, "drawable", context.packageName))
        title.text = item.name
        background_category.setBackgroundColor(getCategoryColor(item.order - 1))
        background_category.setOnClickListener {
            Analytics.trackEvent(Event.selectCategory(item.name))

            context.startActivity(Intent(context, CategoryActivity::class.java)
                    .putExtra(CategoryActivity.CATEGORY_ID_PARAM, item.id)
                    .putExtra(CategoryActivity.CATEGORY_NAME_PARAM, item.name)
            )
        }
    }
}