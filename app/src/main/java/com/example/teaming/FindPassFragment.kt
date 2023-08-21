package com.example.teaming

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.teaming.databinding.ActivitySearchNumBinding
import com.example.teaming.databinding.FragmentChangeNumBinding
import com.example.teaming.databinding.FragmentFindPassBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindPassFragment : Fragment() {
    private lateinit var binding: FragmentFindPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPassBinding.inflate(inflater,container,false)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.TextEmail.addTextChangedListener(textWatcher)

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
                                            val findNumFragment = FindNumFragment()
                                            val bundle = Bundle()
                                            bundle.putString("email",email ) // 여기서 "key"는 값의 식별자, "value"는 실제 전달할 값
                                            findNumFragment.arguments = bundle

                                            requireActivity().supportFragmentManager.beginTransaction()
                                                .replace(R.id.container,findNumFragment)
                                                .addToBackStack(null)
                                                .commit()
                                        }
                                    }
                                }
                            }
                        } else {
                            if(response.code() == 400){
                                val dialog = ThirdDialog2()
                                //dialog.arguments = bundle

                                // Show the success dialog or perform other actions
                                dialog.show(requireActivity().supportFragmentManager, "ThirdDialog2")
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
            //Toast.makeText(this@,"이메일을 입력해주세요", Toast.LENGTH_SHORT).show()

            /*val intent = Intent(this, FindNumActivity::class.java)
                startActivity(intent)*/
        }

        binding.x.setOnClickListener {
            binding.TextEmail.text.clear()
        }
        return binding.root
    }

    private fun updateButtonState() {
        val password = binding.TextEmail.text.toString()

        val enableButton = password.isNotEmpty()

        // Add condition to check hexColor
        if (enableButton) {
            binding.ButtonReset.isEnabled = true
            binding.ButtonReset.setBackgroundResource(R.drawable.round_border4)
        } else {
            binding.ButtonReset.isEnabled = false
            binding.ButtonReset.setBackgroundResource(R.drawable.round_border4)
        }
    }
}