package com.example.ejerciciotema6

import android.view.ContextMenu
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejerciciotema6.databinding.ItemPeliculaBinding
import com.example.ejerciciotema6.model.Pelicula

class PeliculaViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

    val binding = ItemPeliculaBinding.bind(view)
    lateinit var peli:Pelicula

    fun render(item: Pelicula, onClickListener: (Pelicula)-> Unit){
        peli = item
        binding.tvTitle.text=item.title
        //binding.ivPoster.setImageResource(item.poster)
        //Picasso.get().load(item.poster).fit().into(binding.ivPoster)
        Glide.with(binding.ivPoster.context).load(item.poster).fitCenter().into(binding.ivPoster)
        itemView.setOnClickListener{
            onClickListener(item)
        }
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        p0: ContextMenu?,
        p1: View?,
        p2: ContextMenu.ContextMenuInfo?
    ) {
        p0!!.setHeaderTitle(peli.title)
        p0.add(this.adapterPosition, 0, 0, "Eliminar")
    }


}