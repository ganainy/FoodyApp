package com.example.footy.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"


//moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MealApiService {
    @GET("categories.php")
    fun getCategories(): Call<MealCategories>
}

//what rest of app will use so we dont have multiple retrofit instances
object MealApi {
    val retrofitService: MealApiService by lazy {
        //lazy will run the code only once and when called again will return only
        retrofit.create(MealApiService::class.java)

    }
}