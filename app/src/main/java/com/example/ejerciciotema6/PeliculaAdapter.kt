package com.example.ejerciciotema6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejerciciotema6.model.Pelicula

class PeliculaAdapter (private val listaPeli: List<Pelicula>) :
    RecyclerView.Adapter<PeliculaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PeliculaViewHolder(layoutInflater.inflate(R.layout.item_pelicula,parent,false))
    }

    override fun getItemCount(): Int {
        return listaPeli.size
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val item = listaPeli[position]
        holder.render(item)
    }
}