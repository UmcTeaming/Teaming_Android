package com.example.teaming


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login.SearchNumActivity
import com.example.teaming.databinding.ActivityDialogFirstBinding

class DialogFirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_first)

        binding = ActivityDialogFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.num.setOnClickListener {
            val intent = Intent(this, SearchNumActivity::class.java)
            startActivity(intent)
        }

        binding.closeButton.setOnClickListener {
            //dismiss()
        }


    }

}