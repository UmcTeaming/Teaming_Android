package com.example.teaming

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.login.MembershipActivity
import com.example.login.SearchNumActivity
import com.example.teaming.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.ButtonLogin.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }*/

        binding.ButtonGoSearch.setOnClickListener {
            val intent = Intent(this, SearchNumActivity::class.java)
            startActivity(intent)
        }

        binding.ButtonGoMembership.setOnClickListener {
            val intent = Intent(this, MembershipActivity::class.java)
            startActivity(intent)
        }
    }

}