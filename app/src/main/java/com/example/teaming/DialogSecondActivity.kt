package com.example.teaming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teaming.databinding.ActivityDialogSecondBinding


class DialogSecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_second)

        binding.closeButton.setOnClickListener {
            //dismiss()
        }

    }
}