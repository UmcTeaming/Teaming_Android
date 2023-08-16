package com.example.teaming

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.teaming.StartActivity
import com.example.teaming.databinding.ActivityMembershipBinding

class MembershipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMembershipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membership)

        binding = ActivityMembershipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonNext.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }

        binding.ButtonSee.setOnClickListener {
            val intent = Intent(this, AgreeActivity::class.java)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.Button_see);
        button.paintFlags = button.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        button.text = getString(R.string.underlined_text)
    }
}