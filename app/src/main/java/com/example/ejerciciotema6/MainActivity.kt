package com.example.ejerciciotema6

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ejerciciotema6.databinding.ActivityMainBinding
import com.example.ejerciciotema6.model.Pelicula
import com.example.ejerciciotema6.provider.PeliculaProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var listaPeliculas:MutableList<Pelicula>
    //private var myRecyclerView: RecyclerView = findViewById(R.id.rvPeliculas)
    private lateinit var adapter: PeliculaAdapter

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
    }

    private fun cargarLista():MutableList<Pelicula>{
        val lista = mutableListOf<Pelicula>()
        for (pelicula in PeliculaProvider.listaCarga){
            lista.add(pelicula)

        }
        return lista
    }
}