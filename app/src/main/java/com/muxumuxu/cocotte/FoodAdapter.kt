package com.muxumuxu.cocotte

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.l4digital.fastscroll.FastScroller
import com.muxumuxu.cocotte.data.Food
import kotlinx.android.synthetic.main.food_item.view.*
import java.util.*

class FoodAdapter : RecyclerView.Adapter<FoodViewHolder>(), FastScroller.SectionIndexer {

    private var foods: List<Food>

    init {
        foods = ArrayList()
    }

    fun setFoods(foods: List<Food>) {
        this.foods = foods
        notifyDataSetChanged()
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
}

class FoodViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.food_item)) {
    fun bind(food: Food) = with(itemView) {
        risk_icon.setImageResource(context.resources.getIdentifier(food.danger, "drawable", context.packageName))
        risk_icon.contentDescription = food.danger
        name.text = food.name

        food_container.setOnClickListener {
            val intent = Intent(context, FoodActivity::class.java).putExtra(FoodActivity.FOOD_ID_PARAM, food.id)

            val p1 = Pair.create(name as View, context.getString(R.string.food_title_transition_name))
            val p2 = Pair.create(risk_icon as View, context.getString(R.string.food_risk_transition_name))
            context.startActivity(intent, ActivityOptionsCompat.
                    makeSceneTransitionAnimation(context as Activity, p1, p2)
                    .toBundle())
        }
    }
}
