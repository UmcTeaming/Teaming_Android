package com.example.teaming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login.MembershipActivity
import com.example.teaming.databinding.ActivityDialogThirdBinding

class DialogThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_third)

        binding = ActivityDialogThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.member.setOnClickListener {
            val intent = Intent(this, MembershipActivity::class.java)
            startActivity(intent)
        }

        binding.closeButton.setOnClickListener {
            //dismiss()
        }

    }
}