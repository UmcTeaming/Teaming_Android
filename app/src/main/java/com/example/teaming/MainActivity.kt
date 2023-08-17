package com.example.teaming

import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.teaming.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainFragment by lazy { MainFragment() }
    private val calFragment by lazy { CalFragment() }
    private val listFragment by lazy { ListFragment() }
    private val fileFragment by lazy { FileFragment() }
    private val userFragment by lazy { UserFragment() }
    private val fileIcon1Fragment by lazy { File_Icon1_Fragment() }
    private val fileIcon2Fragment by lazy { File_Icon2_Fragment() }

    private val num: Int = 0

    private val finishtimeed: Long = 1000
    private var presstime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


                        var bundle = Bundle()

                        val preferences = getSharedPreferences("memberId", MODE_PRIVATE)

                        val editor = preferences.edit()
                        editor.putInt("memberId", userId)

                        // 변경 사항을 반영하고 저장
                        editor.commit()
                        /*var bundle = Bundle()
                        bundle.putInt("memberId",userId)
                        mainFragment.arguments = bundle
                        fileFragment.arguments = bundle
                        *//*fileIcon1Fragment.arguments = bundle
                        Log.e("메인","${fileIcon1Fragment.arguments}")
                        fileIcon2Fragment.arguments = bundle*/

                        //Log.d("mainId","${userId}")
                        Log.d("R_Login_mainId","${userId}")
                        Log.d("R_Login_mainId","${userId}")

                        App.prefs.token = accessToken

                        Log.d("R_LoginActivity", "Access Token: $accessToken")
                    }
                } else {
                    Log.d("R_LoginActivity", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("R_LoginActivity", "로그인 API 호출 실패", t)
            }
        })

        supportFragmentManager.beginTransaction().add(R.id.container,mainFragment).commit()

        initNavigationBar()
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime: Long = tempTime - presstime
        if (supportFragmentManager.backStackEntryCount == 0) {
            if (0 <= intervalTime && finishtimeed >= intervalTime) {
                finish()
            } else {
                presstime = tempTime
                Toast.makeText(this, "종료하려면 한번 더 누르세요", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            super.onBackPressed()
        }
    }

    private fun initNavigationBar() {
        binding.bottomNavi.run{
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.home -> changeFragment(mainFragment)
                    R.id.cal -> changeFragment(calFragment)
                    R.id.list -> changeFragment(listFragment)
                    R.id.file -> changeFragment(fileFragment)
                    R.id.user -> changeFragment(userFragment)
                }
                true
            }
            selectedItemId = R.id.home
        }
    }
    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }

}
