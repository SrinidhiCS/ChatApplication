package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var edtnamel : EditText
    private lateinit var edtpassl : EditText
    private lateinit var btnloginl : Button
    private lateinit var btnsignupl : Button
    private lateinit var btnresetl : Button

    private lateinit var myaut :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtnamel=findViewById(R.id.user_name)
        edtpassl=findViewById(R.id.pass_word)
        btnloginl=findViewById(R.id.btn_login)
        btnsignupl=findViewById(R.id.btn_signup)
        btnresetl=findViewById(R.id.btn_reset)
        myaut=FirebaseAuth.getInstance()

        supportActionBar?.hide()

        btnsignupl.setOnClickListener{
            var intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }

        btnresetl.setOnClickListener {
            edtnamel.setText("")
            edtpassl.setText("")
        }

        btnloginl.setOnClickListener{
            val email = edtnamel.text.toString()
            val pass = edtpassl.text.toString()

            login(email,pass);
        }
    }

    private fun login(email: String, pass: String) {
        myaut.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@Login , MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "Authentication Failed\n User does not exist", Toast.LENGTH_SHORT).show()
                }
            }

    }
}