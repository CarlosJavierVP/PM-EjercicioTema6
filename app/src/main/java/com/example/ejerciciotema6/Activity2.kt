package com.example.ejerciciotema6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val titulo = intent.getStringExtra("titulo")
        val editTitle = findViewById<EditText>(R.id.editText)
        editTitle.hint = titulo

        val botonCambiar = findViewById<Button>(R.id.btnCambiar)
        botonCambiar.setOnClickListener{
            val intent = Intent()
            val tituloPeli = editTitle.text.toString()
            intent.putExtra("titulo", tituloPeli)
            setResult(RESULT_OK, intent)
            finish()
        }


    }
}