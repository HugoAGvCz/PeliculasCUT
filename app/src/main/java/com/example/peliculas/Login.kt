package com.example.peliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var btnSignup = findViewById<Button>(R.id.btnSingup)
        var btnRecuperar = findViewById<Button>(R.id.btnPassword)
        var email = findViewById<TextView>(R.id.edtTxtEmail)
        var password = findViewById<TextView>(R.id.edtTxtPassword)

        btnLogin.setOnClickListener {
            if (email.text.toString() != "" && password.text.toString() != ""){
                auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){
                        startActivity(Intent(this,MenuPrincipal::class.java).putExtra("Saludos","Menú principal"))
                    }else{
                        Toast.makeText(this,"Error: "+task.exception!!.message.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }
            else{
                Toast.makeText(this,"Campos vacíos",Toast.LENGTH_LONG).show()

            }
        }
        btnRecuperar.setOnClickListener {
            startActivity(Intent(this, RecuperarPassword::class.java).putExtra("saludo", "Recuperar contraseña"))
        }
        btnSignup.setOnClickListener {
            startActivity(Intent(this, CrearCuenta::class.java).putExtra("saludo", "Crear cuenta"))
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "No hay usuarios Autenticados", Toast.LENGTH_LONG).show()
        } else {
            startActivity(Intent(this, MenuPrincipal::class.java))
            finish()
        }
    }
}