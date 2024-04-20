package com.example.peliculas

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.MenuProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MenuPrincipal : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val myRef = database.getReference("peliculas")
    lateinit var peliculas : ArrayList<Pelicula>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        auth = Firebase.auth
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)

                // Read from the database
                myRef.addValueEventListener(object: ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        peliculas = ArrayList<Pelicula>()
                        val value = snapshot.value.toString()
                        Log.d(TAG, "Value is: " + value)

                        snapshot.children.forEach {
                            item ->
                            var pelicula = Pelicula(
                                item.child("nombre").value.toString(),
                                item.child("genero").value.toString(),
                                item.child("anio").value.toString(),
                                item.key.toString())

                            peliculas.add(pelicula)
                        }
                        llenaLista()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }

                })

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.perfil -> true
                    R.id.logout -> {
                        auth.signOut()
                        finish()
                        startActivity(Intent(this@MenuPrincipal, Login::class.java))
                        true
                    }
                    else ->
                        false
                }
            }
        })

        val lista = findViewById<ListView>(R.id.lista)
        lista.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, peliculas[position].nombre.toString(), Toast.LENGTH_LONG).show()

            startActivity(Intent(this, EditarPeliculas::class.java)
                .putExtra("nombre", peliculas[position].nombre.toString())
                .putExtra("genero", peliculas[position].genero.toString())
                .putExtra("anio", peliculas[position].anio.toString())
                .putExtra("id", peliculas[position].id)
            )
        }

        val btnAgregarPeliculas = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.agregarPelicula)
        btnAgregarPeliculas.setOnClickListener {
            var peliVacia = PeliculaDTO("EditarNombre","EditarGenero","EditarAnio")
            myRef.push().setValue(peliVacia)
        }
    }

    fun llenaLista(){
        val adaptador = PeliAdaptador(this, peliculas)
        val lista = findViewById<ListView>(R.id.lista)
        lista.adapter = adaptador
    }
}