package com.javier.proyecto_final_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.javier.proyecto_final_kotlin.databinding.ActivityRegistroUsuariosBinding

class RegistroUsuarios : AppCompatActivity() {
    private lateinit var binding : ActivityRegistroUsuariosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener {
            // registrar
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {result ->
                        if (result.isSuccessful) {
                            Toast.makeText(this,"Usuario Registrado exitosamente", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegistroUsuarios, Login_Sistema::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this,"La Contraseña debe tener minimo 6 caracteres",
                                Toast.LENGTH_SHORT).show()
                        }
                        // mostrar error
                        showAlertError()
                    }
                    }
            }
        }
    private fun showAlertError() {
        val alertBuilder = AlertDialog.Builder(this)
            .setTitle("Ocurrio un error")
            .setMessage("Campos obligatorios a Ingresar")
            .setPositiveButton("Aceptar", null)
        val alert: AlertDialog = alertBuilder.create()
        alert.show()
    }
    }

