package com.javier.proyecto_final_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PantallaCarga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_carga)

        android.os.Handler().postDelayed({
            val intent = Intent(this,Login_Sistema::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}