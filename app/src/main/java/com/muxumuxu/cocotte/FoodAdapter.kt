package com.muxumuxu.cocotte

import android.content.Intent
import android.support.v7.widget.RecyclerView
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
        // FIXME: API side
        this.foods = foods.filter { !it.name.isEmpty() }
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
            context.startActivity(Intent(context, FoodActivity::class.java).putExtra(FoodActivity.FOOD_PARAM, food))
        }
    }
}
