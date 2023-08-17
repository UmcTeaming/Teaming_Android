package com.example.teaming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teaming.FindNumActivity
import com.example.teaming.R
import com.example.teaming.databinding.ActivitySearchNumBinding

class SearchNumActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchNumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_num)

        binding = ActivitySearchNumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonReset.setOnClickListener {
            val intent = Intent(this, FindNumActivity::class.java)
            startActivity(intent)
        }
    }

}