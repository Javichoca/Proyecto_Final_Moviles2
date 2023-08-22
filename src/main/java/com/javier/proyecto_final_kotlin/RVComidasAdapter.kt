package com.javier.proyecto_final_kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.proyecto_final_kotlin.databinding.ItemComidaBinding
import com.javier.proyecto_final_kotlin.model.Comida


class RVComidasAdapter(var comidas: List<Comida>, val onClick:(Comida) ->Unit): RecyclerView.Adapter<RVComidasAdapter.VHComidas>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHComidas {
        val binding = ItemComidaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VHComidas(binding,onClick)
    }

    override fun onBindViewHolder(holder: VHComidas, position: Int) {
        holder.bind(comidas[position])
    }

    override fun getItemCount(): Int = comidas.size
    fun setData(newData: MutableList<Comida>) {
        comidas = newData
        notifyDataSetChanged()

    }

    class VHComidas(private val binding: ItemComidaBinding, val onClick:(Comida)->Unit): RecyclerView.ViewHolder(binding.root) {
        fun bind(comida: Comida) {
            binding.imgMeal.setImageResource(R.drawable.img_meal)
            binding.txtName.text = comida.name
            binding.txtCountry.text = comida.country
            binding.txtType.text = comida.type
            binding.root.setOnClickListener{
                onClick(comida)
            }
        }
    }
}