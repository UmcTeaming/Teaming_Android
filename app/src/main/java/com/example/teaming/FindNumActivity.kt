package com.example.teaming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.teaming.databinding.ActivityFindNumBinding
import com.example.teaming.LoginActivity

class FindNumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindNumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_num)
        binding = ActivityFindNumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")

        binding.resetEmail.text = email
        Log.e("email 넘긴거", "${email}")

        binding.ButtonOkay.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}