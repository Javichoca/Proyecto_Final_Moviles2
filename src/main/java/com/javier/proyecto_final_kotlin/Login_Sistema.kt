package com.javier.proyecto_final_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.javier.proyecto_final_kotlin.databinding.ActivityLoginSistemaBinding

class Login_Sistema : AppCompatActivity() {
    private lateinit var binding: ActivityLoginSistemaBinding
    private lateinit var googleLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSistemaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        googleLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
            try {

                val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                val account = task.getResult(ApiException::class.java)
                if(account != null) {
                    val credencial = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credencial).addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            // nos vamos a home
                            val intent = Intent(this@Login_Sistema, MainActivity::class.java)
                            intent.putExtra("email", account.email)
                            startActivity(intent)
                        } else {
                            // mostrar error
                            showAlertError()
                        }
                    }
                }

            } catch (e: ApiException) {
                showAlertError()
            }
        }
    }

    private fun showAlertError() {
        val alertBuilder = AlertDialog.Builder(this)
            .setTitle("Ocurrio un error")
            .setMessage("Se produjo un error con la autenticación/usuario o Contraseña Incorrecta/Correo no registrado")
            .setPositiveButton("Aceptar", null)
        val alert: AlertDialog = alertBuilder.create()
        alert.show()
    }

    private fun setupViews() {
        binding.btnLogin.setOnClickListener{
            // login
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {result ->
                        if (result.isSuccessful) {
                            Toast.makeText(this,"Inicio de Sesion Exitosa", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@Login_Sistema, MainActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                        } else {
                            // mostrar error
                            showAlertError()
                        }
                    }
            }
        }
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this@Login_Sistema, RegistroUsuarios::class.java)
            startActivity(intent)
        }
        binding.btnGoogle.setOnClickListener {
            Toast.makeText(this,"Cuenta de Google Registrada Exitosamente", Toast.LENGTH_SHORT).show()
            val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id))
                .requestEmail().build()
            val client: GoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)
            val intent= client.signInIntent
            googleLauncher.launch(intent)
        }
    }
}