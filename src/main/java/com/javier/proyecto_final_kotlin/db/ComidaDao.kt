package com.javier.proyecto_final_kotlin.db

import androidx.room.*
import com.javier.proyecto_final_kotlin.model.Comida

@Dao
interface ComidaDao {
    @Query("SELECT * FROM comida")
    fun getFavorites(): List<Comida>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(comida: Comida)

    @Delete
    suspend fun  deleteFavorite(comida: Comida)
}