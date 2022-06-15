package com.example.primerproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.primerproyecto.Classes.User
import com.example.primerproyecto.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var autentication = FirebaseAuth.getInstance()
        var db = FirebaseDatabase.getInstance().getReference("Users")


        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children.mapNotNull { it.getValue<User>() }.toList()

                val user =
                    users.find { x -> x.email == autentication.currentUser!!.email.toString() }


                if (user != null) {
                    binding.txtName.setText(user.name)
                    binding.txtLastName.setText(user.lastName)
                    binding.txtPhone.setText(user.phone)
                    binding.txtEmail.setText(user.email)
                    binding.txtGender.setText(user.gender)
                    binding.txtBirthDate.setText(user.birthDate)
                    binding.txtCountry.setText(user.country)
                    binding.txtCity.setText(user.city)
                    binding.txtAddress.setText(user.address)
                }

            }override fun onCancelled(error : DatabaseError){

            }
        })



        binding.btnLogOut.setOnClickListener {

            autentication.signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}