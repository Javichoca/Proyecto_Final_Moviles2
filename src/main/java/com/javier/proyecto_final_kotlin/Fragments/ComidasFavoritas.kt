package com.javier.proyecto_final_kotlin.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.javier.proyecto_final_kotlin.RVComidasAdapter
import com.javier.proyecto_final_kotlin.databinding.FragmentComidasFavoritasBinding


class ComidasFavoritas : Fragment() {

    private lateinit var binding: FragmentComidasFavoritasBinding
    private lateinit var  viewModel : ComidaFavoritaViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ComidaFavoritaViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComidasFavoritasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RVComidasAdapter(listOf()){ comida ->
            val comidaDetailFragment =  ComidasFavoritasDirections.actionFavoritosFragmentToComidaDetailFragment(comida)
            findNavController().navigate(comidaDetailFragment)

        }
        binding.rvComidaList.adapter = adapter
        binding.rvComidaList.layoutManager = GridLayoutManager(requireContext(),1,RecyclerView.VERTICAL, false)
        viewModel.favorites.observe(requireActivity()){
            adapter.comidas = it
            adapter.notifyDataSetChanged()
        }
        viewModel.getFavorites()
    }



}




