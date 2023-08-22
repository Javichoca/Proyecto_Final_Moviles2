package com.javier.proyecto_final_kotlin.Fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javier.proyecto_final_kotlin.model.ApiResponse
import com.javier.proyecto_final_kotlin.repository.ComidaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComidaLisViewModel (application: Application) : AndroidViewModel(application){
    val repository = ComidaRepository()
    val comidaList: MutableLiveData<List<Comida>> = MutableLiveData<List<Comida>>()

}