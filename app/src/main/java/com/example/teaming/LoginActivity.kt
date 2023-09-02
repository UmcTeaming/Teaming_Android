package com.example.teaming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.teaming.databinding.ActivityLoginBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var isAutoLoginEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(binding.root)

        App.prefs.token = null

        val auto = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val autoLoginUse = auto.getBoolean("autoLoginUse", false)

        if (autoLoginUse){
            setAllViewsToGone(view)
            binding.loading.visibility = View.VISIBLE
            Glide.with(this)
                .asGif()
                .load(R.drawable.loading) // 로딩 중 GIF 이미지 리소스 설정
                .diskCacheStrategy( DiskCacheStrategy.RESOURCE )
                .into(binding.loading)
            val autoId = auto.getString("Id", "")
            val autoPw = auto.getString("Pw", "")
            val requestBodyData = LoginRequset(autoId!!,autoPw!!)

            val json = Gson().toJson(requestBodyData)
            val requestBody = RequestBody.create("application/json".toMediaType(), json)
            val callLogin = RetrofitApi.getRetrofitService.login(requestBody)

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
                            val editor = preferences.edit()

                            editor.putInt("memberId", userId)
                            editor.putString("userName", userName)

                            editor.commit()

                            App.prefs.token = accessToken

                            Log.d("Login_Token", "Access Token: $accessToken")

                            sleep(200)

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Log.d("Login", "API 호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Login", "로그인 API 호출 실패", t)
                }
            })
        }




        binding.ButtonGoSearch.setOnClickListener {
            val intent = Intent(this, SearchNumActivity::class.java)
            startActivity(intent)
        }

        binding.ButtonGoMembership.setOnClickListener {
            val intent = Intent(this, MembershipActivity::class.java)
            startActivity(intent)
        }

        binding.autoLogin.setOnClickListener {
            toggleAutoLogin()
        }

        binding.ButtonLogin.setOnClickListener {
            val logInId = binding.TextEmail.text.toString()
            val password = binding.TextNum.text.toString()

            // 여기서부터 주석 풀거나 걸면 됨
            val requestBodyData = LoginRequset(logInId,password)

            //val requestBodyData = LoginRequset("hyun@gmail.com", "hyun123")

            // 데이터 없는 로그인 정보
            //val requestBodyData = LoginRequset("and@gmail.com", "and123")

            // 데이터 있는 로그인 정보
            //val requestBodyData = LoginRequset("test@gmail.com", "test123")



            val json = Gson().toJson(requestBodyData)
            val requestBody = RequestBody.create("application/json".toMediaType(), json)
            val callLogin = RetrofitApi.getRetrofitService.login(requestBody)

            setAllViewsToGone(view)
            binding.loading.visibility = View.VISIBLE
            Glide.with(this@LoginActivity)
                .asGif()
                .load(R.drawable.loading) // 로딩 중 GIF 이미지 리소스 설정
                .diskCacheStrategy( DiskCacheStrategy.RESOURCE )
                .into(binding.loading)

            callLogin.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            val accessToken = "Bearer ${loginResponse.data.jwtToken.accessToken}"
                            val userId = loginResponse.data.jwtToken.memberId
                            val userName = loginResponse.data.name

                            if (isAutoLoginEnabled){
                                val auto = getSharedPreferences("autoLogin", MODE_PRIVATE)
                                val autoLoginEdit = auto.edit()

                                autoLoginEdit.putBoolean("autoLoginUse", true)
                                autoLoginEdit.putString("Id", logInId)
                                autoLoginEdit.putString("Pw", password)
                                autoLoginEdit.commit()
                            }

                            Log.e("userName","${userName}")

                            var bundle = Bundle()

                            val preferences = getSharedPreferences("memberId", MODE_PRIVATE)
                            val editor = preferences.edit()

                            editor.putInt("memberId", userId)
                            editor.putString("userName", userName)

                            editor.commit()

                            App.prefs.token = accessToken

                            Log.d("Login_Token", "Access Token: $accessToken")

                            setAllViewsToGone(view)

                            binding.loading.visibility = View.VISIBLE

                            sleep(200)

                            Log.d("여기지나감","22")

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Log.d("Login", "API 호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Login", "로그인 API 호출 실패", t)
                }
            })

        }
    }
    private fun toggleAutoLogin() {
        isAutoLoginEnabled = !isAutoLoginEnabled

        if (isAutoLoginEnabled) {
            // 자동 로그인이 활성화된 경우
            binding.autoLoginText.setTextColor(ContextCompat.getColor(this, R.color.blue))
            binding.autoLoginCircle.setBackgroundResource(R.drawable.auto_login_circle_blue)
        } else {
            // 자동 로그인이 비활성화된 경우
            binding.autoLoginText.setTextColor(ContextCompat.getColor(this, R.color.gray))
            binding.autoLoginCircle.setBackgroundResource(R.drawable.auto_login_circle)
        }
    }

    private fun setAllViewsToGone(view: View) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                setAllViewsToGone(child)
            }
        } else {
            view.visibility = View.GONE
        }
    }


}