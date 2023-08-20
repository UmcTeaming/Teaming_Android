package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teaming.databinding.FragmentChangeNumBinding
import com.example.teaming.databinding.FragmentPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordFragment : Fragment() {
    private lateinit var binding: FragmentPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordBinding.inflate(inflater,container,false)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.password.addTextChangedListener(textWatcher)
        binding.passCheck.addTextChangedListener(textWatcher)

        binding.img1.setOnClickListener {
            // 커서 위치
            val cursorPosition = binding.password.selectionStart

            if (binding.password.transformationMethod == null) {
                // 현재 비밀번호 입력 타입이면 일반 텍스트로 변경
                binding.img1.setBackgroundResource(R.drawable.eyeoff)
                binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                // 일반 텍스트 입력 타입이면 비밀번호 형태로 변경
                binding.img1.setBackgroundResource(R.drawable.eyeon)

                binding.password.transformationMethod = null
            }

            // 커서 위치 지정
            binding.password.setSelection(cursorPosition)
        }

        binding.img3.setOnClickListener {
            // 커서 위치
            val cursorPosition = binding.password.selectionStart

            if (binding.passCheck.transformationMethod == null) {
                // 현재 비밀번호 입력 타입이면 일반 텍스트로 변경
                binding.img3.setBackgroundResource(R.drawable.eyeoff)
                binding.passCheck.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                // 일반 텍스트 입력 타입이면 비밀번호 형태로 변경
                binding.img3.setBackgroundResource(R.drawable.eyeon)
                binding.passCheck.transformationMethod = null
            }

            // 커서 위치 지정
            binding.passCheck.setSelection(cursorPosition)
        }

        binding.nextBtn.setOnClickListener {
            val sharedPreference = requireActivity().getSharedPreferences("memberId",
                Context.MODE_PRIVATE)

            val memberId = sharedPreference.getInt("memberId",-1)
            val requestBodyData = ChangePasswordRequest(binding.password.text.toString())
            val checkPass = RetrofitApi.getRetrofitService.changePassword(
                memberId,
                requestBodyData
            )

            checkPass.enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                    if (response.isSuccessful) {
                        val changePassResponse = response.body()
                        if (changePassResponse != null) {
                            Log.e("패스워드 변경 상태","${changePassResponse.status}")
                            val dialog = ChangeCompleteDialog()
                            //dialog.arguments = bundle

                            // Show the success dialog or perform other actions
                            dialog.show(requireActivity().supportFragmentManager, "ChangeCompleteDialog")

                        } else {
                            Log.e("Post 여부", "Post 성공하지만 응답 데이터가 비어있습니다.")
                        }
                    } else {
                        Log.e("Post 여부", "Post 실패: 응답 코드 = ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    Log.e("Post 여부", "Post 실패: 네트워크 또는 기타 오류", t)
                }
            })
        }

        return binding.root
    }

    private fun updateButtonState() {
        val password = binding.password.text.toString()
        val passwordCheck = binding.passCheck.text.toString()

        val enableButton = password.isNotEmpty() && passwordCheck.isNotEmpty() && passwordCheck == password

        // Add condition to check hexColor
        if (enableButton) {
            binding.nextBtn.isEnabled = true
            binding.nextBtn.setBackgroundResource(R.drawable.round_border4)
            binding.img2.setBackgroundResource(R.drawable.checkon)
            binding.img4.setBackgroundResource(R.drawable.checkon)
        } else {
            binding.nextBtn.isEnabled = false
            binding.nextBtn.setBackgroundResource(R.drawable.round_border4)
            binding.img2.setBackgroundResource(R.drawable.checkoff)
            binding.img4.setBackgroundResource(R.drawable.checkoff)
        }
    }

}