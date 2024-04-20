package com.example.peliculas

import android.content.ContentValues.TAG
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.database.database

class EditarPeliculas : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("peliculas")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_peliculas)

        var id = ""
        var img = findViewById<ImageView>(R.id.imgVUPD)
        var nombre = findViewById<EditText>(R.id.txtNombreEdit)
        var genero = findViewById<EditText>(R.id.txtGeneroEdit)
        var anio = findViewById<EditText>(R.id.txtAnioEdit)
        var btnEditar = findViewById<Button>(R.id.btnEditarPelicula)
        var btnEliminar = findViewById<Button>(R.id.btnEliminarPelicula)

        val editaParametros = intent.extras
        nombre.setText(editaParametros?.getCharSequence("nombre").toString())
        genero.setText(editaParametros?.getCharSequence("genero").toString())
        anio.setText(editaParametros?.getCharSequence("anio").toString())
        id = editaParametros?.getCharSequence("id").toString()

        if (editaParametros?.getCharSequence("genero").toString() == "Terror"){
           img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.imgcalaca))
        } else if (editaParametros?.getCharSequence("genero").toString() == "Comedia"){
            img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.imgvivimos))
        } else if (editaParametros?.getCharSequence("genero").toString() == "Drama"){
            img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.imgdrama))
        } else if (editaParametros?.getCharSequence("genero").toString() == "Kaijus"){
            img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gojira))
        } else {
            img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.imgdesconocido))
        }

        btnEditar.setOnClickListener {
            val actualizados = PeliculaDTO(nombre.text.toString(), genero.text.toString(), anio.text.toString())
            myRef.child(id).setValue(actualizados).addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Datos actualizados", Toast.LENGTH_LONG).show()
                    if (genero.text.toString() == "Terror"){
                        img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.imgcalaca))
                    } else if (genero.text.toString() == "Comedia"){
                        img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.imgvivimos))
                    } else if (genero.text.toString() == "Drama"){
                        img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.imgdrama))
                    }  else if (genero.text.toString() == "Kaijus"){
                        img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gojira))
                    } else {
                        img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.imgdesconocido))
                    }
                } else {
                    Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnEliminar.setOnClickListener {
            val builder: AlertDialog.Builder = MaterialAlertDialogBuilder(this)
            builder.setMessage("Esta a punto de eliminar los datos de la pelicula, ¿Desea continuar?")
                .setPositiveButton("Aceptar", DialogInterface.OnClickListener {
                    dialog, which ->
                    eliminarPelicula(id)
                })
                .setNegativeButton("Cancelar", DialogInterface.OnClickListener {
                    dialog, which ->
                })
                .show()
        }
    }

    private fun eliminarPelicula(id: String){
        myRef.child(id).removeValue().addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Datos eliminados", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Error al eliminar los datos", Toast.LENGTH_LONG).show()
            }
        }
    }


}