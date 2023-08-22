package com.javier.proyecto_final_kotlin.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.javier.proyecto_final_kotlin.databinding.ActivityAddFavoriteBinding
import com.javier.proyecto_final_kotlin.model.ComidaDB
import java.util.UUID

class AddFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFavoriteBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var storageRef: StorageReference

    private var selectedImageUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 1
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        storageRef = FirebaseStorage.getInstance().reference

        binding.btnAddImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        binding.btnAddComida.setOnClickListener {
            val name = binding.tilName.editText?.text.toString()
            val country = binding.tilCountry.editText?.text.toString()
            val type = binding.tilType.editText?.text.toString()
            val comida = hashMapOf(
                "nombre" to name,
                "pais" to country,
                "tipo" to type
            )
            if (selectedImageUri != null) {
                val imageRef = storageRef.child("comidas_images/${UUID.randomUUID()}")
                imageRef.putFile(selectedImageUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()
                            comida["img"] = imageUrl
                            saveComidaToFirestore(comida)
                        }
                    }
                    .addOnFailureListener {

                    }
            } else {
                saveComidaToFirestore(comida)
            }
        }
    }
    private fun saveComidaToFirestore(comida: HashMap<String, String>) {
        db.collection("Comida")
            .add(comida)
            .addOnSuccessListener {documentReference ->
                val successMessage =
                    "Comida agregada correctamente con el ID: ${documentReference.id}"
                showAlertDialog("Éxito", successMessage)
            }
            .addOnFailureListener {
                val errorMessage = "No se pudo agregar la comida"
                showAlertDialog("Error", errorMessage)
            }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            binding.ivImage.setImageURI(selectedImageUri)
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
    private fun getData() {
        db.collection("Comida").get()
            .addOnSuccessListener { querySnapshot ->
                val comidas = mutableListOf<ComidaDB>()
                for (document in querySnapshot.documents) {
                    val comida = ComidaDB(
                        img = document.getString("img") ?: "",
                        name = document.getString("nombre") ?: "",
                        country = document.getString("pais") ?: "",
                        type = document.getString("tipo") ?: ""
                    )
                    comidas.add(comida)
                }

            }
    }
}
