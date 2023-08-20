package com.example.teaming

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teaming.databinding.ActivitySearchNumBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchNumActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchNumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_num)

        binding = ActivitySearchNumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonReset.setOnClickListener {
            val email = binding.TextEmail.text.toString()

            if(email.isNotEmpty()){
                val resetPass = RetrofitApi.getRetrofitService.resetPassword(email)
                resetPass.enqueue(object : Callback<MemberResetPasswordResponse> {
                    override fun onResponse(
                        call: Call<MemberResetPasswordResponse>,
                        response: Response<MemberResetPasswordResponse>
                    ) {
                        if (response.isSuccessful) {
                            val passResponse = response.body()
                            if (passResponse != null) {
                                val passProjects = passResponse.status
                                val passMessage = passResponse.message
                                Log.e("pass_status","${passProjects}")
                                Log.e("pass_msg","${passMessage}")
                                if(passProjects != null){
                                    when(passProjects){
                                        200-> {
                                            val intent = Intent(this@SearchNumActivity, FindNumActivity::class.java)
                                            intent.putExtra("email",email)
                                            startActivity(intent)
                                        }
                                        /*400 -> {
                                            val intent = Intent(this@SearchNumActivity, DialogThirdActivity::class.java)
                                            startActivity(intent)
                                        }*/
                                    }
                                }
                            }
                        } else {
                            if(response.code() == 400){
                                val dialog = ThirdDialog()
                                // Show the success dialog or perform other actions
                                dialog.show(supportFragmentManager, "ThirdDialog")
                                /*val intent = Intent(this@SearchNumActivity, DialogThirdActivity::class.java)
                                startActivity(intent)*/
                            }
                            //Log.d("Fragment", "API 반호출 실패: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<MemberResetPasswordResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
            //binding.ButtonReset.isClickable = false
            Toast.makeText(this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show()

        /*val intent = Intent(this, FindNumActivity::class.java)
            startActivity(intent)*/
        }

        binding.x.setOnClickListener {
            binding.TextEmail.text.clear()
        }
    }

}