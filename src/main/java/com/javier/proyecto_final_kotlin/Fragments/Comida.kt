package com.javier.proyecto_final_kotlin.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.javier.proyecto_final_kotlin.RVComidasAdapter
import com.javier.proyecto_final_kotlin.databinding.FragmentComidaBinding
import com.javier.proyecto_final_kotlin.model.Comida



class Comida : Fragment() {

    private lateinit var binding: FragmentComidaBinding
    private lateinit var db: FirebaseFirestore
    private var comidaList: MutableList<Comida> = mutableListOf()
    private lateinit var adapter: RVComidasAdapter
    private lateinit var  viewModel : ComidaLisViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComidaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        val collection = db.collection("Comida")

        val linearManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvComidas.layoutManager = linearManager

        adapter = RVComidasAdapter(comidaList) { comida ->
            addToFavorites(comida)
            navigateToDetail(comida)
        }
        binding.rvComidas.adapter = adapter

        collection.addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            value?.let {
                comidaList.clear()
                for (document in it) {
                    val name = document.getString("nombre") ?: ""
                    val country = document.getString("pais") ?: ""
                    val type = document.getString("tipo") ?: ""
                    val comidaAdd = Comida(id,name, country, type)
                    comidaList.add(comidaAdd)
                }
                adapter.notifyDataSetChanged()
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterComidas(newText)
                return true
            }
        })
    }
    private fun addToFavorites(comida: Comida) {
        comidaList.add(comida)
    }

    private fun navigateToDetail(comida: Comida) {
        val confirmMessage = "¿Quieres ver el detalle de la comida que es ${comida.name},${comida.country},${comida.type}?"

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmación")
        builder.setMessage(confirmMessage)
        builder.setPositiveButton("Sí") { dialog, which ->
            val comidaDetailFragment = ComidaDirections.actionComidaFragmentToComidaDetailFragment(comida)
            findNavController().navigate(comidaDetailFragment)
        }
        builder.setNegativeButton("No", null)
        val dialog = builder.create()
        dialog.show()
    }
    private fun filterComidas(query: String?) {
        val filteredList = mutableListOf<Comida>()
        query?.let {
            val searchQuery = it.toLowerCase().trim()
            for (comida in comidaList) {
                if (comida.name.toLowerCase().contains(searchQuery) ||
                    comida.country.toLowerCase().contains(searchQuery) ||
                    comida.type.toLowerCase().contains(searchQuery)) {
                    filteredList.add(comida)
                }
            }
        }
        adapter.setData(filteredList)
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }
    }
}