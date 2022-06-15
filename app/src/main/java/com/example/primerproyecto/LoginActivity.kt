package com.example.primerproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.primerproyecto.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var autentication = FirebaseAuth.getInstance()
        var db = FirebaseDatabase.getInstance().getReference("Users")


        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(autentication.currentUser != null){

            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }


        binding.btnLogin.setOnClickListener {

            var userMail = binding.txtLoginMail.text.toString()
            var userPass = binding.txtLoginPassword.text.toString()

            if(TextUtils.isEmpty(binding.txtLoginMail.text)){
                binding.txtLoginMail.error = "Se debe ingresar el correo"

                if(!Patterns.EMAIL_ADDRESS.matcher(userMail).matches()){

                    binding.txtLoginMail.error = "Formato de correo inválido"
                }
            }
            else if (TextUtils.isEmpty(binding.txtLoginPassword.text)){
                binding.txtLoginPassword.error = "Se debe ingresar la contraseña"
            }
            else{

                autentication.signInWithEmailAndPassword(userMail,userPass)
                    .addOnSuccessListener {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {

                            x ->  Toast.makeText(this,"Ha ocurrido un error debido a ${x.message}",
                        Toast.LENGTH_LONG).show()
                    }
            }
        }

        binding.btnSignUp.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.btnRestore.setOnClickListener {

            startActivity(Intent(this, RestoreActivity::class.java))
            finish()
        }
    }
}