package com.javier.proyecto_final_kotlin.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.proyecto_final_kotlin.MainViewModel
import androidx.lifecycle.Observer
import com.javier.proyecto_final_kotlin.RVMealAdapter
import com.javier.proyecto_final_kotlin.databinding.FragmentApiBinding
import androidx.appcompat.widget.SearchView
import com.javier.proyecto_final_kotlin.model.DataMeal


class Api : Fragment() {
    private lateinit var binding: FragmentApiBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mealsAdapter: RVMealAdapter
    private  var originalMealsList: List<DataMeal> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.meals.observe(viewLifecycleOwner, Observer {
            originalMealsList = it?.meals ?: emptyList()
            mealsAdapter.setData(originalMealsList)
        })

        viewModel.getMeals()
    }

    private fun setViews() {
        mealsAdapter = RVMealAdapter(listOf())
        val linearManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMeals.layoutManager = linearManager
        binding.rvMeals.adapter = mealsAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMeals(newText)
                return true
            }
        })
    }

    private fun filterMeals(query: String?) {
        val filteredList = mutableListOf<DataMeal>()
        query?.let {
            val searchQuery = it.toLowerCase().trim()
            for (meal in originalMealsList) {
                if (meal.strMeal.toLowerCase().contains(searchQuery) ||
                    meal.strCategory.toLowerCase().contains(searchQuery) ||
                    meal.strMealThumb.toLowerCase().contains(searchQuery)
                ) {
                    filteredList.add(meal)
                }
            }
        }
        mealsAdapter.setData(filteredList)
    }

}
