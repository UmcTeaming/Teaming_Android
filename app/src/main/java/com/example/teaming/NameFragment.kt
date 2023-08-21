package com.example.teaming

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teaming.databinding.FragmentCreateBinding
import com.example.teaming.databinding.FragmentNameBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NameFragment : Fragment() {
    private lateinit var binding: FragmentNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNameBinding.inflate(inflater,container,false)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()

                //performPatchRequest(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.edittext.addTextChangedListener(textWatcher)

        //updateButtonState()

        binding.okayBtn.setOnClickListener {
            val sharedPreference = requireActivity().getSharedPreferences("memberId",
                Context.MODE_PRIVATE)

            val memberId = sharedPreference.getInt("memberId",-1)
            val requestBodyData = ChangeNicknameRequest(binding.edittext.text.toString())
            val checkPass = RetrofitApi.getRetrofitService.changeNickname(
                memberId,
                requestBodyData
            )

            checkPass.enqueue(object : Callback<ChangeNicknameResponse> {
                override fun onResponse(call: Call<ChangeNicknameResponse>, response: Response<ChangeNicknameResponse>) {
                    if (response.isSuccessful) {
                        val changeNickResponse = response.body()
                        if (changeNickResponse != null) {
                            Log.e("패스워드 상태","${changeNickResponse.status}")

                            binding.textStatus.text = "좋은 닉네임이네요 :)"
                            binding.textStatus.setTextColor(Color.parseColor("#527FF5"))

                            val dialog = NickCompletDialog()
                            //dialog.arguments = bundle

                            // Show the success dialog or perform other actions
                            dialog.show(requireActivity().supportFragmentManager, "NickCompletDialog")

                        } else {
                            Log.e("Post 여부", "Post 성공하지만 응답 데이터가 비어있습니다.")
                        }
                    } else {
                        //val changeNickResponse = response.body()
                        if(response.code()==400 ){
                            binding.textStatus.text = "중복되는 닉네임이 있어요."
                            binding.textStatus.setTextColor(Color.parseColor("#80FF0000"))
                        }
                        Log.e("Post 여부", "Post 실패: 응답 코드 = ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ChangeNicknameResponse>, t: Throwable) {
                    Log.e("Post 여부", "Post 실패: 네트워크 또는 기타 오류", t)
                }
            })

        }

        return binding.root
    }

    private fun updateButtonState() {
        val nickname = binding.edittext.text.toString()

        val enableButton = nickname.isNotEmpty()

        // Add condition to check hexColor
        if (enableButton) {
            binding.okayBtn.isEnabled = true
            binding.okayBtn.setBackgroundResource(R.drawable.round_border5)
        } else {
            binding.okayBtn.isEnabled = false
            binding.okayBtn.setBackgroundResource(R.drawable.round_border5)
        }
    }
}