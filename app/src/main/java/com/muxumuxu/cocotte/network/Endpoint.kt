package com.muxumuxu.cocotte.network

import com.muxumuxu.cocotte.data.Food
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val BASE_URL = "https://pregnant-foods.herokuapp.com"

interface Endpoint {
    @GET("/foods.json")
    fun fetchFoods(): Single<List<Food>>

    companion object {

        private var INSTANCE: Endpoint? = null

        fun getInstance(): Endpoint =
                INSTANCE ?: synchronized(Endpoint::class) {
                    INSTANCE ?: buildService().also { INSTANCE = it }
                }

        private fun buildService() =
                Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(MoshiConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(Endpoint::class.java)
    }
}