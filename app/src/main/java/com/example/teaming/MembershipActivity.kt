package com.example.teaming

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.teaming.databinding.ActivityMembershipBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembershipActivity : AppCompatActivity(), DialogAgreeFragment.DialogListener {

    private lateinit var binding: ActivityMembershipBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var toggleState: String? = null

    private var btn1 = false
    private var btn2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membership)


        binding = ActivityMembershipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            binding.TextName.isEnabled = isChecked
            binding.TextEmail.isEnabled = isChecked
            binding.TextConfirm.isEnabled = isChecked
            binding.TextNum.isEnabled = isChecked
            binding.TextNumCheck.isEnabled = isChecked

            if (!isChecked) {
                // 토글 버튼이 꺼져 있는 경우에는 EditText 내용 초기화
                binding.TextName.text.clear()
                binding.TextEmail.text.clear()
                binding.TextConfirm.text.clear()
                binding.TextNum.text.clear()
                binding.TextNumCheck.text.clear()
            }

            updateButtonState() // 상태 업데이트
        }*/

        updateButtonState() // 상태 업데이트

        /*sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding.TextName.setText(sharedPreferences.getString("name", ""))
        binding.TextEmail.setText(sharedPreferences.getString("email", ""))
        binding.TextConfirm.setText(sharedPreferences.getString("confirm", ""))
        binding.TextNum.setText(sharedPreferences.getString("num", ""))
        binding.TextNumCheck.setText(sharedPreferences.getString("check", ""))*/


        /*val toggleState = intent.getStringExtra("toggleState")
        Log.d("MembershipActivity", "Received toggleState: $toggleState")*/

        if(toggleState == "true"){
            binding.toggleButton.isChecked = true
            binding.text10.visibility = View.INVISIBLE
            binding.toggleButton.isClickable = false

            updateToggleButtonColor(true)
        }


        binding.ButtonNext.setOnClickListener {
            //val intent = Intent(this, AgreeActivity::class.java)
            //startActivity(intent)

            val name = binding.TextName.text.toString()
            val email = binding.TextEmail.text.toString()
            val password = binding.TextNum.text.toString()
            val sharedPreference = getSharedPreferences("memberId",
                Context.MODE_PRIVATE
            )
            val memberId = sharedPreference.getInt("memberId",-1)
            Log.e("프로젝트 생성시 memberId","${memberId}")

            val requestBodyData = MemberRequestDto(name, email, password)
            val signup = RetrofitApi.getRetrofitService.signup(
                requestBodyData
            )

            signup.enqueue(object : Callback<MemberRequestDtoResponse> {
                override fun onResponse(call: Call<MemberRequestDtoResponse>, response: Response<MemberRequestDtoResponse>) {
                    if (response.isSuccessful) {
                        val createProjectResponse = response.body()
                        if (createProjectResponse != null) {
                            val intent = Intent(this@MembershipActivity, StartActivity::class.java)
                            startActivity(intent)

                            Log.e("가입하기 여부","가입 성공")

                            } else {
                            Log.e("Post 여부", "Post 성공하지만 응답 데이터가 비어있습니다.")
                        }
                    } else {
                        Log.e("Post 여부", "Post 실패: 응답 코드 = ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MemberRequestDtoResponse>, t: Throwable) {
                    Log.e("Post 여부", "Post 실패: 네트워크 또는 기타 오류", t)
                }
            })
        }

        val button = findViewById<TextView>(R.id.Button_see);
        button.paintFlags = button.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        button.text = getString(R.string.underlined_text1)

        binding.ButtonResend.text = "인증하기"
        binding.ButtonResend.setOnClickListener{
            if(binding.TextEmail.text.isNotEmpty()){
                val requestBodyData = MemberDuplicationRequest(binding.TextEmail.text.toString())
                val memberdup = RetrofitApi.getRetrofitService.memberduplication(
                    requestBodyData
                )

                memberdup.enqueue(object : Callback<MemberDuplicationResponse> {
                    override fun onResponse(call: Call<MemberDuplicationResponse>, response: Response<MemberDuplicationResponse>) {
                        if (response.isSuccessful) {
                            val memberdupResponse = response.body()
                            if (memberdupResponse != null) {
                                Log.e("Post 여부", "성공")
                                binding.ButtonResend.text = "재전송하기"
                                btn1 = true
                            }
                            else {
                                Log.e("Post 여부", "Post 성공하지만 응답 데이터가 비어있습니다.")
                            }
                        } else {
                            if(response.code() == 400){
                                binding.ButtonResend.text = "인증하기"
                                val dialog = DialogFirstFragment()
                                //dialog.arguments = bundle
                                // Show the success dialog or perform other actions
                                dialog.show(supportFragmentManager, "DialogFirstFragment")
                            }
                            Log.e("Post 여부", "Post 실패: 응답 코드 = ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<MemberDuplicationResponse>, t: Throwable) {
                        binding.ButtonResend.text = "인증하기"
                        Log.e("Post 여부", "Post 실패: 네트워크 또는 기타 오류", t)
                    }
                })
            }
        }

        binding.ButtonCheck.setOnClickListener{
                val requestBodyData = MemberVerificationRequest(binding.TextConfirm.text.toString())
                val memberverfi = RetrofitApi.getRetrofitService.memberverification(
                    requestBodyData
                )

                memberverfi.enqueue(object : Callback<MemberVerificationResponse> {
                    override fun onResponse(call: Call<MemberVerificationResponse>, response: Response<MemberVerificationResponse>) {
                        if (response.isSuccessful) {
                            val memberverfiResponse = response.body()
                            if (memberverfiResponse != null) {
                                Log.e("Post 여부", "성공")
                                btn2 = true

                            } else {
                                Log.e("Post 여부", "Post 성공하지만 응답 데이터가 비어있습니다.")
                            }
                        } else {
                            if(response.code() == 400){
                                val dialog = DialogSecondFragment()
                                //dialog.arguments = bundle
                                // Show the success dialog or perform other actions
                                dialog.show(supportFragmentManager, "DialogSecondFragment")
                            }
                            Log.e("Post 여부", "Post 실패: 응답 코드 = ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<MemberVerificationResponse>, t: Throwable) {
                        Log.e("Post 여부", "Post 실패: 네트워크 또는 기타 오류", t)
                    }
                })

        }

        binding.ButtonSee.setOnClickListener {
            val dialog = DialogAgreeFragment()
            dialog.setDialogListener(this)
            dialog.show(supportFragmentManager, "DialogAgreeFragment")
        }


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*with(sharedPreferences.edit()) {
                    putString("name", binding.TextName.text.toString())
                    putString("email", binding.TextEmail.text.toString())
                    putString("confirm", binding.TextConfirm.text.toString())
                    putString("num", binding.TextNum.text.toString())
                    putString("check", binding.TextNumCheck.text.toString())
                    apply()
                }*/
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

        if(toggleState == "true"){
            binding.toggleButton.isChecked = true
            binding.text11.visibility = View.INVISIBLE
            binding.toggleButton.isClickable = false

            updateToggleButtonColor(true)
        }

        val imageViewCheckNum = findViewById<ImageView>(R.id.check1)
        val imageViewCheckNumCheck = findViewById<ImageView>(R.id.check2)
        val ButtonCheck = confirm.isNotEmpty() && email.isNotEmpty()

        Log.e("조건 확인", "name: $name, email: $email, confirm: $confirm, num: $num, check: $check")

        val enableButton = num.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && confirm.isNotEmpty() && check.isNotEmpty()
        //val toggle = findViewById<ToggleButton>(R.id.toggleButton)

        Log.e("조건 확인", "enableButton: $enableButton, toggle.isChecked: ${toggleState}, num == check: ${num == check}")
        Log.e("조건 확인", "${btn1}, ${btn2}")

        if (enableButton  && num == check && toggleState == "true" && btn1 && btn2) {
            binding.ButtonNext.isEnabled = true
            binding.ButtonNext.setBackgroundResource(R.drawable.round_border3)
            /*binding.ButtonNext.setOnClickListener {
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
            }*/
        }
        else {
            Log.e("조건 확인", "조건이 만족하지 않음: enableButton=$enableButton, toggle.isChecked=${toggleState}, num=$num, check=$check")
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

        if(ButtonCheck){
            binding.ButtonCheck.isEnabled = true
            binding.ButtonCheck.setBackgroundResource(R.drawable.round_border3)
        }
        else{
            binding.ButtonNext.isEnabled = false
            binding.ButtonNext.setBackgroundResource(R.drawable.round_border3)
        }

    }

    override fun onDialogButtonClicked(value: String) {
        toggleState = value
        updateToggleButtonColor(value == "true")
        updateButtonState()
    }

    // 토글 버튼의 색상을 업데이트하는 함수
    private fun updateToggleButtonColor(isChecked: Boolean) {
        if (isChecked) {
            binding.toggleButton.setBackgroundResource(R.drawable.round_border5)
        } else {
            binding.toggleButton.setBackgroundResource(R.drawable.round_border3)
        }
    }
}