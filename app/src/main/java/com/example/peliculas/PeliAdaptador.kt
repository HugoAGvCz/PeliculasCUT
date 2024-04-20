package com.example.peliculas

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class PeliAdaptador(private val contex:Activity, private val arrayList: ArrayList<Pelicula>) :
    ArrayAdapter<Pelicula>(contex, R.layout.item, arrayList) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(contex)
        val view : View = inflater.inflate(R.layout.item, null)

        view.findViewById<TextView>(R.id.nombrePeli).text = arrayList[position].nombre
        view.findViewById<TextView>(R.id.generoPeli).text = arrayList[position].genero
        view.findViewById<TextView>(R.id.anioPeli).text = arrayList[position].anio

        if (arrayList[position].genero == "Terror"){
            view.findViewById<ImageView>(R.id.imagenPeli).setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.imgcalaca))
        } else if (arrayList[position].genero == "Comedia"){
            view.findViewById<ImageView>(R.id.imagenPeli).setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.imgvivimos))
        } else if (arrayList[position].genero == "Drama"){
            view.findViewById<ImageView>(R.id.imagenPeli).setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.imgdrama))
        } else if (arrayList[position].genero == "Kaijus"){
            view.findViewById<ImageView>(R.id.imagenPeli).setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.gojira))
        } else {
            view.findViewById<ImageView>(R.id.imagenPeli).setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.imgdesconocido))
        }

        return view
        //return super.getView(position, convertView, parent)
    }
}