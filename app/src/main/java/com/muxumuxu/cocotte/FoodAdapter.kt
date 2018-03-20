package com.muxumuxu.cocotte

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.l4digital.fastscroll.FastScroller
import com.muxumuxu.cocotte.analytics.Analytics
import com.muxumuxu.cocotte.analytics.Event
import com.muxumuxu.cocotte.data.Food
import kotlinx.android.synthetic.main.food_item.view.*

class FoodAdapter(private var source: String, private var info: String?)
    : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(), FastScroller.SectionIndexer {

    private var foods: List<Food> = ArrayList()

    fun setFoods(foods: List<Food>) {
        DiffUtil.calculateDiff(FoodDiffUtil(foods, this.foods)).dispatchUpdatesTo(this)
        this.foods = foods
    }

    override fun getSectionText(position: Int): String {
        return foods[position].name[0].toString()
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        return holder.bind(foods[position])
    }

    inner class FoodViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.food_item, false)) {
        fun bind(food: Food) = with(itemView) {
            risk_icon.setImageResource(context.resources.getIdentifier(food.danger, "drawable", context.packageName))
            risk_icon.contentDescription = food.danger
            name.text = food.name

            food_container.setOnClickListener {
                Analytics.trackEvent(Event.selectFood(food.name, this@FoodAdapter.source, this@FoodAdapter.info))

                val intent = Intent(context, FoodActivity::class.java).putExtra(FoodActivity.FOOD_ID_PARAM, food.id)

                val p1 = Pair.create(name as View, context.getString(R.string.food_title_transition_name))
                val p2 = Pair.create(risk_icon as View, context.getString(R.string.food_risk_transition_name))
                context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, p1, p2)
                        .toBundle())
            }
        }
    }
}

private class FoodDiffUtil(private var newFoods: List<Food>, private var oldFoods: List<Food>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldFoods.size
    }

    override fun getNewListSize(): Int {
        return newFoods.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newFoods[newItemPosition].id == oldFoods[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}