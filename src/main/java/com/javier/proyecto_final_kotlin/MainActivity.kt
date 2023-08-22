package com.javier.proyecto_final_kotlin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.javier.proyecto_final_kotlin.Fragments.AddFavoriteActivity
import com.javier.proyecto_final_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var emailExtra: String
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailExtra = intent.getStringExtra("email")!!
        title = "Bienvenidos al Sistema"
        setupNavController()
        saveEmailToSharedPreferences(emailExtra)
        binding.fabAddComida.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Confirmación")
            alertDialogBuilder.setMessage("¿Deseas seguir registrando más platos de comida?")
            alertDialogBuilder.setPositiveButton("Sí") { _, _ ->
                val intent = Intent(this, AddFavoriteActivity::class.java)
                startActivity(intent)
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
    private fun saveEmailToSharedPreferences(email: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.apply()
    }
    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_comida) as NavHostFragment
        navController = navHostFragment.navController
        binding.bnvMenu.setupWithNavController(navController)
    }

}



