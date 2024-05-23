package com.example.tugas_besar_pam

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class EditProfileActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var nameEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var imageProfile: ImageView
    private lateinit var usiaEditText: EditText
    private lateinit var jkSpinner: Spinner
    private lateinit var bioEditText: EditText

    private var imageUri: Uri? = null

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        saveButton = findViewById(R.id.simpanButton)
        imageProfile = findViewById(R.id.imageProfile)
        nameEditText = findViewById(R.id.nameEditText)
        usiaEditText = findViewById(R.id.usiaEditText)
        bioEditText = findViewById(R.id.bioEditText)
        jkSpinner = findViewById(R.id.jkSpinner)

        val genderOptions = arrayOf("Laki-laki", "Perempuan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jkSpinner.adapter = adapter

        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }

        // Register the ActivityResultLauncher for picking image
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                imageUri = data?.data
                imageProfile.setImageURI(imageUri)
            }
        }

        imageProfile.setOnClickListener {
            // Pilih gambar dari galeri
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val usia = usiaEditText.text.toString().trim()
            val jk = jkSpinner.selectedItem.toString().trim()
            val bio = bioEditText.text.toString().trim()

            if (name.isEmpty() || usia.isEmpty() || jk.isEmpty() || bio.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUser = firebaseAuth.currentUser
            currentUser?.uid?.let { userId ->
                val userDocRef = db.collection("users").document(userId)

                // Ambil data pengguna dari Firestore
                userDocRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val currentData = document.data ?: hashMapOf()

                            // Gabungkan data baru dengan data lama
                            val updateUserData = currentData.toMutableMap()
                            updateUserData["name"] = name
                            updateUserData["usia"] = usia
                            updateUserData["jk"] = jk
                            updateUserData["bio"] = bio

                            // Simpan data yang diperbarui ke Firestore
                            userDocRef.set(updateUserData)
                                .addOnSuccessListener {
                                    uploadProfileImage(userId)
                                }
                                .addOnFailureListener { exception ->
                                    println("Error updating document: $exception")
                                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        println("Error getting document: $exception")
                        Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }

    private fun uploadProfileImage(userId: String) {
        imageUri?.let { uri ->
            val storageRef = storage.reference.child("profile_images/$userId.jpg")
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
