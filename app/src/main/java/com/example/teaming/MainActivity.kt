package com.example.teaming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.teaming.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainFragment by lazy { MainFragment() }
    private val nonMainFragment by lazy {MainFragment1()}
    private val calFragment by lazy { CalFragment() }
    private val listFragment by lazy { ListFragment() }
    private val nonListFragment by lazy { ListFragment1() }
    private val fileFragment by lazy { FileFragment() }
    private val userFragment by lazy { UserFragment() }
    private val fileIcon1Fragment by lazy { File_Icon1_Fragment() }
    private val fileIcon2Fragment by lazy { File_Icon2_Fragment() }
    private val createFragment by lazy { CreateFragment() }

    private val num: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestBodyData = LoginRequset("test@gmail.com", "test123")
        val json = Gson().toJson(requestBodyData)
        val requestBody = RequestBody.create("application/json".toMediaType(), json)
        val callLogin = RetrofitApi.getRetrofitService.login(requestBody)

        callLogin.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val accessToken = loginResponse.data.accessToken
                        App.prefs.token=accessToken
                        Log.d("LoginActivity", "Access Token: $accessToken")
                    }
                } else {
                    Log.d("LoginActivity", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "로그인 API 호출 실패", t)
            }
        })

        /*callLogin.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val accessToken = loginResponse.data.accessToken
                        Log.d("LoginActivity", "Access Token: $accessToken")
                    }
                } else {
                    Log.d("LoginActivity", "Login Failed")
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LoginActivity", "Login API Call Failed")
            }
        })*/


        initNavigationBar()
    }

    private fun initNavigationBar() {
        binding.bottomNavi.run{
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.home ->
                    {
                        // project가 없는 경우
                        //changeFragment(nonMainFragment)

                        // project가 있는 경우
                        changeFragment(mainFragment)
                    }
                    R.id.cal -> changeFragment(calFragment)
                    R.id.list ->
                    {
                        // project가 없는 경우
                        //changeFragment(nonListFragment)
                        // project가 있는 경우
                        changeFragment(listFragment)
                    }
                    R.id.file -> changeFragment(fileFragment)
                    R.id.user -> changeFragment(userFragment)
                }
                true
            }
            selectedItemId = R.id.home
        }
    }

    fun openFragment(int: Int){
        when(int){
            1 ->
            {
                // 조건문
                changeFragment(fileFragment)
            }
        }
        true
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }
}