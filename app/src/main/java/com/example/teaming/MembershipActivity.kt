package com.example.teaming

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
        button.text = getString(R.string.underlined_text1)


        /*val requestBodyData = MemberRequestDto("홍길동", "test@gmail.com", "test123")
        val json = Gson().toJson(requestBodyData)
        val requestBody = RequestBody.create("application/json".toMediaType(), json)
        val callSignup = RetrofitApi.getRetrofitService.signup(requestBody)

        callSignup.enqueue(object : Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    val SignupResponse = response.body()
                    if (SignupResponse != null) {
                        val accessToken = "Bearer ${SignupResponse.data.jwtToken.accessToken}"
                        val userId = SignupResponse.data.jwtToken.memberId

                        var bundle = Bundle()

                        val preferences = getSharedPreferences("memberId", MODE_PRIVATE)

                        val editor = preferences.edit()
                        editor.putInt("memberId", userId)

                        editor.commit()

                        App.prefs.token = accessToken

                        Log.d("Login_Token", "Access Token: $accessToken")
                    }
                } else {
                    Log.d("Login", "API 호출 실패: ${response.code()}")
                }
            }
                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.e("Login", "로그인 API 호출 실패", t)
            }
        })*/

    }
}