package com.example.ejerciciotema6

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.ejerciciotema6.databinding.ActivityMainBinding
import com.example.ejerciciotema6.model.Pelicula
import com.example.ejerciciotema6.provider.PeliculaProvider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityMainBinding
    private lateinit var listaPeliculas:MutableList<Pelicula>
    private lateinit var adapter: PeliculaAdapter
    private lateinit var layoutManager: LayoutManager

    private lateinit var intentLaunch:ActivityResultLauncher<Intent>

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


        var titleMovie = findViewById<TextView>(R.id.tvTitle)
        titleMovie.text="Datos no obtenidos"
        //preparar la variable intentLaunch para recibir los datos de la activity2
        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            result: ActivityResult ->
            if(result.resultCode== RESULT_OK){
                title = result.data?.extras?.getString("titulo").toString()
                titleMovie.text = "$title"
            }
        }

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

    private fun display(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val peliModificada: Pelicula = listaPeliculas[item.groupId]

        when (item.itemId){
            0 ->{
                val alert = AlertDialog.Builder(this).setTitle("Eliminar ${peliModificada}")
                    .setMessage("¿Estás seguro de que quieres eliminar ${peliModificada}?")
                    .setNeutralButton("Cerrar",null)
                    .setPositiveButton("Aceptar"){_,_ ->
                        display("Se ha eliminado ${peliModificada.title}")
                        listaPeliculas.removeAt(item.groupId)
                        adapter.notifyItemRemoved(item.groupId)
                        adapter.notifyItemRangeChanged(item.groupId, listaPeliculas.size)
                        binding.rvPeliculas.adapter = PeliculaAdapter(listaPeliculas){pelicula ->
                            onItemSelected(pelicula)
                        }
                    }.create()
                alert.show()
            }
            else -> super.onContextItemSelected(item)
        }
        return true
    }


        override fun onClick(p0: View?) {
            val intent = Intent(this,Activity2::class.java)
            intent.putExtra("titulo", title)
            intentLaunch.launch(intent)
        }




}