package com.muxumuxu.cocotte

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorites_menu -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

        fun bind(item: Category) = with(itemView) {
            cover.setImageResource(context.resources.getIdentifier(item.image, "drawable", context.packageName))
            title.text = item.name
            background_category.setBackgroundColor(getCategoryColor(item.order - 1))
            background_category.setOnClickListener {
                context.startActivity(Intent(context, CategoryActivity::class.java).putExtra(CategoryActivity.CATEGORY_PARAM, item))
            }
        }
    }
}
