package com.muxumuxu.cocotte.analytics

import org.json.JSONObject

sealed class Event(val name: String, val params: JSONObject)

open class SelectCategoryEvent(name: String) :
        Event("SelectCategory", JSONObject().put("name", name))

open class SelectFoodEvent(foodName: String, source: String, search: String?) :
        Event("SelectFood", JSONObject()
                .put("name", foodName)
                .put("from", source)
                .put("search", search))

open class FavorizeEvent(foodName: String, add: Boolean) :
        Event(if (add) "AddFav" else "RemoveFav", JSONObject().put("name", foodName))

open class ShareEvent(foodName: String, categoryName: String) :
        Event("Share", JSONObject()
                .put("food", foodName)
                .put("category", categoryName))
