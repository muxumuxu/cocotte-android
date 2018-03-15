package com.muxumuxu.cocotte.analytics

import org.json.JSONObject

// TODO: Move to sealed class with inheritance
open class Event(val name: String, val params: JSONObject) {
    companion object {
        fun selectCategory(categoryName: String) =
                Event("SelectCategory", JSONObject().put("name", categoryName))

        fun selectFood(foodName: String, source: String, search: String?) =
                Event("SelectFood", JSONObject()
                        .put("name", foodName)
                        .put("from", source)
                        .put("search", search))

        fun favorize(foodName: String, add: Boolean) =
                Event(if (add) "AddFav" else "RemoveFav", JSONObject().put("name", foodName))

        fun share(foodName: String, categoryName: String) =
                Event("Share", JSONObject()
                        .put("food", foodName)
                        .put("category", categoryName))

    }
}

