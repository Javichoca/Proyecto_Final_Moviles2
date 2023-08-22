package com.javier.proyecto_final_kotlin.Fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.javier.proyecto_final_kotlin.db.ComidaDataBase
import com.javier.proyecto_final_kotlin.repository.ComidaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.javier.proyecto_final_kotlin.model.Comida

class ComidaDetailViewModel (application: Application) : AndroidViewModel(application) {
    private val repository :  ComidaRepository
    init{
        val db = ComidaDataBase.getDatabase(application)
        repository = ComidaRepository(db.comidaDao())
    }
    fun addFavorite(comida: Comida){
        viewModelScope.launch (Dispatchers.IO){
            repository.addFavorite(comida)
        }
    }
    fun deleteFavorite(comida: Comida){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteFavorite(comida)
        }
    }
}