package com.example.peliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CrearCuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        var saludo = findViewById<TextView>(R.id.saludoCrear)
        var btnRegresar = findViewById<Button>(R.id.btnRegresarCrear)
        val parametros = intent.extras

        saludo.text = parametros?.getCharSequence("saludo").toString()

        btnRegresar.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}