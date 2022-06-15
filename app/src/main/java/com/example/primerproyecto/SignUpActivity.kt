package com.example.primerproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.primerproyecto.Classes.User
import com.example.primerproyecto.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var autentication = FirebaseAuth.getInstance()
        var db = FirebaseDatabase.getInstance().getReference("Users")


        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSingUp.setOnClickListener {

            if(TextUtils.isEmpty(binding.txtName.text)){

                binding.txtName.error = "Se debe ingresar el nombre"
            }
            else if(TextUtils.isEmpty(binding.txtLastName.text)){
                binding.txtLastName.error = "Se debe ingresar el apellido"
            }
            else if(TextUtils.isEmpty(binding.txtPhone.text)){
                binding.txtPhone.error = "Se debe ingresar el telefono"
            }
            else if(TextUtils.isEmpty(binding.txtEmail.text)){
                binding.txtEmail.error = "Se debe ingresar el correo"
            }
            else if(TextUtils.isEmpty(binding.txtBirthDate.text)){
                binding.txtBirthDate.error = "Se debe ingresar la fecha de nacimiento"
            }
            else if(TextUtils.isEmpty(binding.txtCountry.text)){
                binding.txtCountry.error = "Se debe ingresar el pais"
            }
            else if(TextUtils.isEmpty(binding.txtCity.text)){
                binding.txtCity.error = "Se debe ingresar la provincia"
            }
            else if(TextUtils.isEmpty(binding.txtAddress.text)){
                binding.txtAddress.error = "Se debe ingresar la direccion"
            }
            else if(TextUtils.isEmpty(binding.txtPassword.text)){
                binding.txtPassword.error = "Se debe ingresar la contraseña"
            }
            else if(binding.txtPassword.text.length < 6){
                binding.txtPassword.error = "La contraseña debe tener mínimo 6 carácteres"
            }
            else if(TextUtils.isEmpty(binding.txtConfirmPass.text)){
                binding.txtConfirmPass.error = "Se debe ingresar la confirmacion"
            }
            else if (binding.txtPassword.text.toString() != binding.txtConfirmPass.text.toString()){

                binding.txtConfirmPass.error = "Las contraseñas no coinciden"
            }
            else{

                var gender = if(binding.rdbFemenino.isChecked)
                    "femenino"
                else
                    "masculino"

                val id = db.push().key.toString()

                val user = User(
                    id,
                    binding.txtName.text.toString(),
                    binding.txtLastName.text.toString(),
                    binding.txtPhone.text.toString(),
                    binding.txtEmail.text.toString(),
                    gender,
                    binding.txtBirthDate.text.toString(),
                    binding.txtCountry.text.toString(),
                    binding.txtCity.text.toString(),
                    binding.txtAddress.text.toString(),
                    binding.txtPassword.text.toString()
                )

                db.child(id).setValue(user).addOnSuccessListener {
                    autentication.createUserWithEmailAndPassword(
                        binding.txtEmail.text.toString(),
                        binding.txtPassword.text.toString()
                    ).addOnSuccessListener {
                        Toast.makeText(this, "Usuario agregado", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {

                            x ->  Toast.makeText(this,"Ha ocurrido un error debido a ${x.message}",
                        Toast.LENGTH_LONG).show()
                    }

                    Toast.makeText(this, "Usuario registrado", Toast.LENGTH_LONG).show()

                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }

                .addOnFailureListener {

                        x ->  Toast.makeText(this,"Ha ocurrido un error debido a ${x.message}",
                    Toast.LENGTH_LONG).show()
                }
            }


        }
    }
}