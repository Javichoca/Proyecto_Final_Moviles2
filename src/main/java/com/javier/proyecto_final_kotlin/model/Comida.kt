package com.javier.proyecto_final_kotlin.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Entity(tableName="comida")
@Parcelize
data class Comida(
    @PrimaryKey
    val id:Int,
    val name: String,
    val country: String,
    val type: String,
    var isFavorite: Boolean =false
): Parcelable

