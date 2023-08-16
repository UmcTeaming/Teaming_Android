package com.example.teaming

import android.graphics.Paint
import android.content.Intent
import android.media.AudioPlaybackCaptureConfiguration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.teaming.R
import com.example.teaming.databinding.ActivityMypageBinding

class MypageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding.pencil.setOnClickListener {
            setFragment()
        }

        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.secret.setOnClickListener {
            val intent = Intent(this, ChangeNum::class.java)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.Button_mail);
        button.paintFlags = button.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        button.text = getString(R.string.underlined_text2)
    }

    private fun setFragment() {
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, NameFragment())
        transaction.commit()
    }
}