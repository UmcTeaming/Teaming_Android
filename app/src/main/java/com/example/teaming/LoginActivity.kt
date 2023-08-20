package com.example.teaming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.teaming.SearchNumActivity
import com.example.teaming.MembershipActivity
import com.example.teaming.databinding.ActivityLoginBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.ButtonGoSearch.setOnClickListener {
            val intent = Intent(this, SearchNumActivity::class.java)
            startActivity(intent)
        }

        binding.ButtonGoMembership.setOnClickListener {
            val intent = Intent(this, MembershipActivity::class.java)
            startActivity(intent)
        }

        binding.ButtonLogin.setOnClickListener {
            val logInId = binding.TextEmail.text.toString()
            val password = binding.TextNum.text.toString()

            // 여기서부터 주석 풀면됨
            /*val requestBodyData = LoginRequset(logInId,password)

            //val requestBodyData = LoginRequset("hyun@gmail.com", "hyun123")

            // 데이터 없는 로그인 정보
            //val requestBodyData = LoginRequset("and@gmail.com", "and123")

            // 데이터 있는 로그인 정보
            //val requestBodyData = LoginRequset("test@gmail.com", "test123")



            val json = Gson().toJson(requestBodyData)
            val requestBody = RequestBody.create("application/json".toMediaType(), json)
            val callLogin = RetrofitApi.getRetrofitService.login(requestBody)

            App.prefs.token = null

            callLogin.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            val accessToken = "Bearer ${loginResponse.data.jwtToken.accessToken}"
                            val userId = loginResponse.data.jwtToken.memberId
                            val userName = loginResponse.data.name

                            Log.e("userName","${userName}")

                            var bundle = Bundle()

                            val preferences = getSharedPreferences("memberId", MODE_PRIVATE)
                            val preferences2 = getSharedPreferences("memberName", MODE_PRIVATE)

                            val editor = preferences.edit()
                            val editor2 = preferences2.edit()

                            editor.putInt("memberId", userId)
                            editor2.putString("memberName",userName)

                            editor.commit()
                            editor2.commit()

                            App.prefs.token = accessToken

                            Log.d("Login_Token", "Access Token: $accessToken")

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Log.d("Login", "API 호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Login", "로그인 API 호출 실패", t)
                }
            })*/

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

}