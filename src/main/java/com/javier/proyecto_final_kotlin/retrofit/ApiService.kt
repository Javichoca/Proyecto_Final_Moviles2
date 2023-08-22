package com.javier.proyecto_final_kotlin.retrofit

import com.javier.proyecto_final_kotlin.model.Meals
import retrofit2.http.GET

interface ApiService {

    @GET("v1/1/search.php?f=b")
    suspend fun getMeals(): Meals
}