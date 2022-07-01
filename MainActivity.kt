package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userrecyclerview:RecyclerView
    private lateinit var userlist:ArrayList<users>
    private lateinit var adapter:useradapt
    private lateinit var mauth: FirebaseAuth
    private lateinit var mydbref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Chat Application"

        mauth = FirebaseAuth.getInstance()
        userlist=ArrayList()
        adapter = useradapt(this,userlist)
        userrecyclerview = findViewById(R.id.user_recyclerView)
        mydbref = FirebaseDatabase.getInstance().reference

        userrecyclerview.layoutManager = LinearLayoutManager(this)
        userrecyclerview.adapter=adapter

        mydbref.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(users::class.java)
                    if (currentUser != null) {
                        if (mauth.currentUser?.uid != currentUser.uid) {
                            userlist.add(currentUser!!)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            mauth.signOut()
            val intent = Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}