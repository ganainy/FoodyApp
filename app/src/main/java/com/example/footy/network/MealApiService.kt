package com.example.footy.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//                             https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"


//moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MealApiService {
    @GET("categories.php")
    fun getCategoriesAsync(): Deferred<MealCategories>

    @GET("filter.php")
    fun getMealsOfCategoryAsync(@Query("c") categoryName: String): Deferred<MealsOfCertainCategory>
}

//what rest of app will use so we don't have multiple retrofit instances
object MealApi {

    val retrofitService: MealApiService by lazy {
        //lazy will run the code only once and when called again will return only
        retrofit.create(MealApiService::class.java)

    }
}