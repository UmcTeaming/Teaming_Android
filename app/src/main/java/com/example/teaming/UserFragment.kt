package com.example.teaming


import android.content.Context
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.teaming.databinding.FragmentUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFragment : Fragment() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: FragmentUserBinding
    private lateinit var backCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserBinding.inflate(inflater,container,false)
        fragmentManager = requireActivity().supportFragmentManager

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val memberId = sharedPreference.getInt("memberId",-1)

        val callMyPage = RetrofitApi.getRetrofitService.myPage(memberId)

        callMyPage.enqueue(object : Callback<MyPageResponse> {
            override fun onResponse(call: Call<MyPageResponse>, response: Response<MyPageResponse>) {
                if (response.isSuccessful) {
                    val mypageResponse = response.body()
                    if (mypageResponse != null) {

                        val username = mypageResponse.data.name

                        binding.name.text = username

                        Log.d("R_LoginActivity", "${mypageResponse.data}")
                    }
                } else {
                    Log.d("R_LoginActivity", "API 호출 실패: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<MyPageResponse>, t: Throwable) {
                Log.e("R_LoginActivity", "로그인 API 호출 실패", t)
            }
        })


        binding.secret.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,ChangeNum())
                .addToBackStack(null)
                .commit()
        }

        binding.ButtonPencil.setOnClickListener {

            binding.name.visibility = View.INVISIBLE
            binding.ButtonPencil.visibility = View.INVISIBLE
            binding.nim.visibility = View.INVISIBLE

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout,NameFragment())
                .addToBackStack(null)
                .commit()


        }

        backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.name.visibility = View.VISIBLE
                binding.ButtonPencil.visibility = View.VISIBLE
                binding.nim.visibility = View.VISIBLE

                fragmentManager.popBackStackImmediate()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backCallback.remove()
    }

}