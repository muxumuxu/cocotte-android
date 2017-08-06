package com.muxumuxu.cocotte

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.muxumuxu.cocotte.data.Category
import com.muxumuxu.cocotte.data.Food
import com.muxumuxu.cocotte.network.Endpoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.category_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CategoriesAdapter()
        categories.adapter = adapter
        categories.layoutManager = GridLayoutManager(this, 2)
        Retrofit.Builder().baseUrl("https://pregnant-foods.herokuapp.com").addConverterFactory(MoshiConverterFactory.create()).build().create(Endpoint::class.java).fetchFoods().enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                Store.foods = response.body()
                adapter.setCategories(Store.foods.map { it.category }.distinct().sortedBy { it.order })
            }

            override fun onFailure(call: Call<List<Food>>?, t: Throwable?) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

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

        // TODO: Get colors from API or something
        val colors = arrayOf("#484291", "#FFDB3A", "#007CFF", "#9F57B7", "#99D22A", "#FFDA3A",
                "#99D22A", "#484291", "#FFDA3A", "#9F57B6", "#FFDA3A", "#99D22A")

        fun bind(item: Category) = with(itemView) {
            cover.setImageResource(context.resources.getIdentifier(item.image, "drawable", context.packageName))
            title.text = item.name
            background_category.setBackgroundColor(Color.parseColor(colors[item.order - 1]))
            background_category.setOnClickListener {
                context.startActivity(Intent(context, CategoryActivity::class.java).putExtra(CategoryActivity.CATEGORY_PARAM, item))
            }
        }
    }
}
