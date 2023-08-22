package com.javier.proyecto_final_kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.javier.proyecto_final_kotlin.databinding.ItemMealBinding
import com.javier.proyecto_final_kotlin.model.DataMeal



class RVMealAdapter(private var meals: List<DataMeal>) : RecyclerView.Adapter<RVMealAdapter.VHMeal>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHMeal {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VHMeal(binding)
    }

    override fun onBindViewHolder(holder: VHMeal, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount(): Int = meals.size

    fun setData(newData: List<DataMeal>) {
        meals = newData
        notifyDataSetChanged()
    }

    class VHMeal(private val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: DataMeal) {
            binding.txtName.text = meal.strMeal
            binding.txtCategory.text = meal.strCategory
            Glide.with(binding.root)
                .load(meal.strMealThumb)
                .centerCrop()
                .into(binding.imgMeal)
        }
    }
}
