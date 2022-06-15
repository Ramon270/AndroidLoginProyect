package com.example.primerproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.primerproyecto.databinding.ActivityLoginBinding
import com.example.primerproyecto.databinding.ActivityRestoreBinding
import com.google.firebase.auth.FirebaseAuth

class RestoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRestorePass.setOnClickListener {

            FirebaseAuth.getInstance().sendPasswordResetEmail(binding.txtRestoreEmail.text.toString().trim())
                .addOnSuccessListener {
                    Toast.makeText(this, "Mensaje de restauración enviado", Toast.LENGTH_LONG).show()

                }.addOnFailureListener {

                        x ->  Toast.makeText(this,"Ha ocurrido un error debido a ${x.message}",
                    Toast.LENGTH_LONG).show()
                }
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}