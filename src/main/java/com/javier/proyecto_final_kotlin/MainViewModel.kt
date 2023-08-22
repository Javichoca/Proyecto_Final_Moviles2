package com.javier.proyecto_final_kotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.javier.proyecto_final_kotlin.model.Meals
import com.javier.proyecto_final_kotlin.retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel: ViewModel() {
    val meals: MutableLiveData<Meals> = MutableLiveData()

    fun getMeals() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitBuilder.apiService.getMeals()
            println(response)
            withContext(Dispatchers.Main) {
                meals.postValue(response)
            }
        }
    }
}