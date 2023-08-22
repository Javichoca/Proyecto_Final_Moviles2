package com.javier.proyecto_final_kotlin.repository

import com.javier.proyecto_final_kotlin.Fragments.ComidaListResponse
import com.javier.proyecto_final_kotlin.db.ComidaDao
import com.javier.proyecto_final_kotlin.model.ApiResponse
import com.javier.proyecto_final_kotlin.model.Comida

class ComidaRepository (val comidaDao: ComidaDao? = null) {

    suspend fun addFavorite(comida: Comida){
        comidaDao?.let{
            it.addFavorite(comida)
        }
    }

    suspend fun deleteFavorite(comida: Comida){
        comidaDao?.let{
            it.deleteFavorite(comida)
        }
    }

    fun getFavorites():List<Comida>{
        comidaDao?.let{
            return it.getFavorites()
        }?:kotlin.run{
            return listOf()
        }
    }

}