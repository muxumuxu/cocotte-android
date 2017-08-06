package com.muxumuxu.cocotte.network

import com.muxumuxu.cocotte.data.Food
import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {
    @GET("/foods.json")
    fun fetchFoods(): Call<List<Food>>
}