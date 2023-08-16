package com.example.teaming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login.MembershipActivity
import com.example.teaming.databinding.ActivityAgreeBinding

class AgreeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agree)

        binding = ActivityAgreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonAgree.setOnClickListener {
            val intent = Intent(this, MembershipActivity::class.java)
            startActivity(intent)
        }

    }
}