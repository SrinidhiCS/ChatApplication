package com.example.chatapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    private lateinit var myaut : FirebaseAuth
    private lateinit var edtnames : EditText
    private lateinit var edtpasss : EditText
    private lateinit var edtemails : EditText
    private lateinit var btnsignups : Button
    private lateinit var btnexits : Button
    private lateinit var btnresets : Button
    private lateinit var mydbref :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()

        myaut=FirebaseAuth.getInstance()

        btnresets=findViewById(R.id.btn_reset)
        btnexits=findViewById(R.id.btn_exit)
        btnsignups=findViewById(R.id.btn_signup)
        edtnames=findViewById(R.id.user_name)
        edtemails=findViewById(R.id.user_email)
        edtpasss=findViewById(R.id.pass_word)

        btnresets.setOnClickListener {
            edtnames.setText("")
            edtpasss.setText("")
            edtemails.setText("")
        }

        btnexits.setOnClickListener {
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        btnsignups.setOnClickListener {
            val email = edtemails.text.toString()
            val pass = edtpasss.text.toString()
            val name = edtnames.text.toString()
            signup(name,email, pass)
        }

    }
    private fun signup(name:String, email: String, pass:String){
        myaut.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    //jump to home page
                    addusertodb(name,email, myaut.currentUser?.uid!!)
                    val intent = Intent(this@Signup , MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Signup, "Sign-In Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addusertodb(name:String,email:String,uid:String) {
        mydbref = FirebaseDatabase.getInstance().reference
        mydbref.child("user").child(uid).setValue(users(name,uid,email))

    }


}