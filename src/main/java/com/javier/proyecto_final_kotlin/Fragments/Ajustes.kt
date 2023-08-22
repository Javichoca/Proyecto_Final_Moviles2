package com.javier.proyecto_final_kotlin.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.javier.proyecto_final_kotlin.databinding.FragmentAjustesBinding
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Base64
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory


class Ajustes : Fragment() {

    private lateinit var binding: FragmentAjustesBinding
    private lateinit var openCameraLauncher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAjustesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedEmail = getEmailFromSharedPreferences()
        binding.txtEmail.text = savedEmail
        val savedImage = getProfileImageFromSharedPreferences()
        if (savedImage != null) {
            binding.imageView.setImageBitmap(savedImage)
        }
        binding.btnLogout.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Cerrar Sesión")
            alertDialogBuilder.setMessage("¿Estás seguro de que deseas cerrar sesión?")

            alertDialogBuilder.setPositiveButton("Sí") { dialog, _ ->
                Toast.makeText(requireContext(), "Cierre de Sesión Exitosa", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                activity?.finish()
            }

            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        binding.btnAgregarFoto.setOnClickListener {
            if (permissionValidated()){
                openCamera()
            }
        }
        openCameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val photo = result.data?.extras?.get("data") as Bitmap
                binding.imageView.setImageBitmap(photo)
                saveProfileImageToSharedPreferences(photo)
            }
        }
    }
    private fun getEmailFromSharedPreferences(): String {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", "") ?: ""
    }
    private fun permissionValidated(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        )
        val permissionList: MutableList<String> = mutableListOf()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.CAMERA)
        }
        if (permissionList.isNotEmpty()) {
            requestPermissions(permissionList.toTypedArray(), 1000)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(requireContext(), "Permiso de Cámara DENEGADO", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openCamera() {
        val intent = Intent()
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE)
        openCameraLauncher.launch(intent)
    }
    private fun saveProfileImageToSharedPreferences(imageBitmap: Bitmap) {
        val encodedImage = encodeBitmapToBase64(imageBitmap)
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("profileImage", encodedImage)
        editor.apply()
    }
    private fun encodeBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    private fun getProfileImageFromSharedPreferences(): Bitmap? {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val encodedImage = sharedPreferences.getString("profileImage", null)
        return if (encodedImage != null) {
            decodeBase64ToBitmap(encodedImage)
        } else {
            null
        }
    }

    private fun decodeBase64ToBitmap(encodedImage: String): Bitmap {
        val decodedByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
    }
}