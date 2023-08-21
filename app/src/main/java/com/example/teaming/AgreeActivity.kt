package com.example.teaming

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.teaming.MembershipActivity
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
            intent.putExtra("toggleState", "true")
            startActivity(intent)
        }
    }


}