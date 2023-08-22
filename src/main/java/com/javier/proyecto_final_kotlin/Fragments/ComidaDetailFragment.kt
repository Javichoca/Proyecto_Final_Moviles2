package com.javier.proyecto_final_kotlin.Fragments

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.javier.proyecto_final_kotlin.databinding.FragmentComidaDetailBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.javier.proyecto_final_kotlin.R
import com.javier.proyecto_final_kotlin.model.Comida
import androidx.appcompat.app.AlertDialog

class ComidaDetailFragment : Fragment() {

    private lateinit var binding: FragmentComidaDetailBinding
    private lateinit var  viewModel : ComidaDetailViewModel
    val args:ComidaDetailFragmentArgs by navArgs()
    private lateinit var comida: Comida
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        comida = args.comida
        viewModel = ViewModelProvider(requireActivity()).get(ComidaDetailViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComidaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgMeal.setImageResource(R.drawable.img_meal)
        binding.txtName.text = comida.name
        binding.txtCountry.text = comida.country
        binding.txtType.text = comida.type
        binding.btnBackToComida.apply{
            visibility = if(comida.isFavorite) View.GONE else View.VISIBLE
        }
        binding.btnBackToComida.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Confirmación")
            alertDialogBuilder.setMessage("¿Estás seguro de que deseas agregar esta comida a favoritos?")
            alertDialogBuilder.setPositiveButton("Sí") { _, _ ->
                comida.isFavorite = true
                viewModel.addFavorite(comida)
                Toast.makeText(requireContext(), "Comida agregada a Favoritos", Toast.LENGTH_SHORT).show()
            }
            alertDialogBuilder.setNegativeButton("Cancelar", null)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        binding.btndeleteComida.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Confirmación")
            alertDialogBuilder.setMessage("¿Estas seguro que deseas eliminar esta comida?")
            alertDialogBuilder.setPositiveButton("Sí") { _, _ ->
                comida.isFavorite = false
                viewModel.deleteFavorite(comida)
                Toast.makeText(requireContext(), "Comida eliminada correctamente", Toast.LENGTH_SHORT).show()
            }
            alertDialogBuilder.setNegativeButton("Cancelar", null)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }
}