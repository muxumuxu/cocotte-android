package com.muxumuxu.cocotte.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.muxumuxu.cocotte.data.Food
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
                        .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create()))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(Endpoint::class.java)
    }
}