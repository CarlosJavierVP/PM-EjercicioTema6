package com.example.ejerciciotema6

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ejerciciotema6.databinding.ItemPeliculaBinding
import com.example.ejerciciotema6.model.Pelicula

class PeliculaViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val binding = ItemPeliculaBinding.bind(view)

    fun render(item: Pelicula){
        binding.tvTitle.text=item.title
        binding.ivPoster.setImageResource(item.poster)
    }
}