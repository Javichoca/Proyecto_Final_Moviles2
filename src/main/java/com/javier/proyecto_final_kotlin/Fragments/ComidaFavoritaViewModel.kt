package com.javier.proyecto_final_kotlin.Fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javier.proyecto_final_kotlin.db.ComidaDataBase
import com.javier.proyecto_final_kotlin.repository.ComidaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.javier.proyecto_final_kotlin.model.Comida
class ComidaFavoritaViewModel (application: Application) : AndroidViewModel(application) {
    private val repository : ComidaRepository
    private val _favorites: MutableLiveData<List<Comida>> =  MutableLiveData()
    val favorites : LiveData<List<Comida>> = _favorites
    init{
        val db = ComidaDataBase.getDatabase(application)
        repository = ComidaRepository(db.comidaDao())
    }
    fun getFavorites(){
        viewModelScope.launch (Dispatchers.IO){
            val data = repository.getFavorites()
            _favorites.postValue(data)
        }
    }
}