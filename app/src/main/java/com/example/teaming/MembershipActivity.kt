package com.example.teaming

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProvider
import com.example.teaming.StartActivity
import com.example.teaming.AgreeActivity
import com.example.teaming.databinding.ActivityMembershipBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembershipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMembershipBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membership)


        binding = ActivityMembershipBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //내용 저장
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding.TextName.setText(sharedPreferences.getString("name", ""))
        binding.TextEmail.setText(sharedPreferences.getString("email", ""))
        binding.TextConfirm.setText(sharedPreferences.getString("confirm", ""))
        binding.TextNum.setText(sharedPreferences.getString("num", ""))
        binding.TextNumCheck.setText(sharedPreferences.getString("check", ""))


        val toggleState = intent.getStringExtra("toggleState")
        Log.d("MembershipActivity", "Received toggleState: $toggleState")

        if(toggleState == "true"){
            binding.toggleButton.isChecked = true
            binding.text10.visibility = View.INVISIBLE
            binding.toggleButton.isClickable = false
        }

        binding.ButtonSee.setOnClickListener {
            val intent = Intent(this, AgreeActivity::class.java)

            startActivity(intent)
        }

        val button = findViewById<TextView>(R.id.Button_see);
        button.paintFlags = button.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        button.text = getString(R.string.underlined_text1)


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                with(sharedPreferences.edit()) {
                    putString("name", binding.TextName.text.toString())
                    putString("email", binding.TextEmail.text.toString())
                    putString("confirm", binding.TextConfirm.text.toString())
                    putString("num", binding.TextNum.text.toString())
                    putString("check", binding.TextNumCheck.text.toString())
                    apply()
                }
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.TextName.addTextChangedListener(textWatcher)
        binding.TextEmail.addTextChangedListener(textWatcher)
        binding.TextConfirm.addTextChangedListener(textWatcher)
        binding.TextNum.addTextChangedListener(textWatcher)
        binding.TextNumCheck.addTextChangedListener(textWatcher)

    }


    private fun updateButtonState() {
        val name = binding.TextName.text.toString()
        val email = binding.TextEmail.text.toString()
        val confirm = binding.TextConfirm.text.toString()
        val num = binding.TextNum.text.toString()
        val check = binding.TextNumCheck.text.toString()

        val imageViewCheckNum = findViewById<ImageView>(R.id.check1)
        val imageViewCheckNumCheck = findViewById<ImageView>(R.id.check2)

        Log.e("조건 확인", "name: $name, email: $email, confirm: $confirm, num: $num, check: $check")

        val enableButton = num.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && confirm.isNotEmpty() && check.isNotEmpty()
        val toggle = findViewById<ToggleButton>(R.id.toggleButton)


        Log.e("조건 확인", "enableButton: $enableButton, toggle.isChecked: ${toggle.isChecked}, num == check: ${num == check}")

        if (enableButton && toggle.isChecked && num == check) {
            binding.ButtonNext.isEnabled = true
            binding.ButtonNext.setBackgroundResource(R.drawable.round_border3)
            binding.ButtonNext.setOnClickListener {
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
            }
        }
        else {
            Log.e("조건 확인", "조건이 만족하지 않음: enableButton=$enableButton, toggle.isChecked=${toggle.isChecked}, num=$num, check=$check")
            binding.ButtonNext.isEnabled = false
            binding.ButtonNext.setBackgroundResource(R.drawable.round_border3)
        }

        if (num.isNotEmpty() && check.isNotEmpty() && num == check) {
            imageViewCheckNum.setImageResource(R.drawable.checkon)
            imageViewCheckNumCheck.setImageResource(R.drawable.checkon)
        } else {
            imageViewCheckNum.setImageResource(R.drawable.checkoff)
            imageViewCheckNumCheck.setImageResource(R.drawable.checkoff)
        }

    }
}