package com.example.ejerciciotema6

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.ejerciciotema6.databinding.ActivityMainBinding
import com.example.ejerciciotema6.model.Pelicula
import com.example.ejerciciotema6.provider.PeliculaProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var listaPeliculas:MutableList<Pelicula>
    private lateinit var adapter: PeliculaAdapter
    private lateinit var layoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listaPeliculas = cargarLista()
        layoutManager = LinearLayoutManager(this)
        binding.rvPeliculas.layoutManager=layoutManager
        adapter = PeliculaAdapter(listaPeliculas){pelicula ->
            onItemSelected(pelicula)
        }
        binding.rvPeliculas.adapter = adapter

        //manejar los espacios entre los items del RecyclerView
        binding.rvPeliculas.setHasFixedSize(true)
        binding.rvPeliculas.itemAnimator = DefaultItemAnimator()

    }


    private fun cargarLista():MutableList<Pelicula>{
        val lista = mutableListOf<Pelicula>()
        for (pelicula in PeliculaProvider.listaCarga){
            lista.add(pelicula)
        }
        return lista
    }

    private fun onItemSelected(pelicula: Pelicula){
        Toast.makeText(
            this,
            "Duración: ${pelicula.time} minutos - Año: ${pelicula.year}",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.recargar ->{
                recarga()
                true
            }

            R.id.limpiar ->{
                limpia()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun limpia(){
        listaPeliculas.clear()
        this.adapter.notifyItemRangeRemoved(0,listaPeliculas.size)
        binding.rvPeliculas.adapter = PeliculaAdapter(listaPeliculas){ pelicula ->
            onItemSelected(pelicula)
        }
    }

    private fun recarga(){
        listaPeliculas = cargarLista()
        adapter.notifyItemRangeInserted(0,listaPeliculas.size)
        binding.rvPeliculas.adapter = PeliculaAdapter(listaPeliculas){ pelicula ->
            onItemSelected(pelicula)
        }
    }






}