package com.example.teaming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.teaming.LoginActivity
import com.example.teaming.databinding.ActivityFirstBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)*/

        binding.ButtonStart.setOnClickListener {

            /*// 테스트용 정보
            //val requestBodyData = LoginRequset("mapledt001@naver.com", "cat123")

            // 데이터 없는 로그인 정보
            //val requestBodyData = LoginRequset("and@gmail.com", "and123")

            // 데이터 있는 로그인 정보
            val requestBodyData = LoginRequset("test@gmail.com", "test123")
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
                            //val preferences2 = getSharedPreferences("memberName", MODE_PRIVATE)

                            val editor = preferences.edit()
                            //val editor2 = preferences2.edit()

                            editor.putInt("memberId", userId)
                            //editor2.putString("memberName",userName)

                            editor.putString("userName", userName)

                            editor.commit()
                            //editor2.commit()

                            App.prefs.token = accessToken

                            Log.d("Login_Token", "Access Token: $accessToken")
                        }
                    } else {
                        Log.d("Login", "API 호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Login", "로그인 API 호출 실패", t)
                }
            })*/

            val intent = Intent(this, Onboarding1::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }*/
}