package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.teaming.databinding.FragmentFileBinding
import com.example.teaming.databinding.FragmentListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private var memberName:String? = null

    // 색상 변경을 위해 선택되었는지 아닌지 확인하는 변수
    private var isFileIcon1Selected = true
    private var isFileIcon2Selected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        memberName = sharedPreference.getString("userName", "Loading name failed")
        binding.memberName.text = memberName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )

        val memberId = sharedPreference.getInt("memberId",-1)
        Log.e("포트폴리오 id","${memberId}")

        val sharedPreference2 = requireActivity().getSharedPreferences("memberName",
            Context.MODE_PRIVATE
        )

        val memberName = sharedPreference2.getString("memberName", "카리나")
        binding.memberName.text = memberName

        Log.e("리스트프래그, name", "${memberName}")

        val callProgressPage = RetrofitApi.getRetrofitService.progressPage(memberId)

        if(memberId!=null){
            callProgressPage.enqueue(object : Callback<ProgressPageResponse> {
                override fun onResponse(
                    call: Call<ProgressPageResponse>, response: Response<ProgressPageResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e("포트폴리오 memberId","${memberId}")
                        val progressPageResponse = response.body()
                        if (progressPageResponse != null ) {
                            //val name = progressPageResponse.data.
                            val progressProjects = progressPageResponse.data.progressProjects
                            Log.e("tag","${progressProjects}")

                            binding.memberName.text = progressPageResponse.data.member_name
                            Log.e("List 사용자명","${progressPageResponse.data.member_name}")

                            if(progressProjects !=null && progressProjects.isNotEmpty()){
                                binding.btnLayout.visibility = View.VISIBLE
                                binding.fileFrame.visibility = View.VISIBLE
                                binding.nonViewPager2.visibility = View.INVISIBLE
                            }
                            else{
                                binding.btnLayout.visibility = View.INVISIBLE
                                binding.fileFrame.visibility = View.GONE
                                binding.nonViewPager2.visibility = View.VISIBLE
                            }
                            /*binding.potVerList.visibility = View.GONE
                            binding.icon1Non.visibility = View.VISIBLE*/
                        }
                    } else {
                        Log.d("FileFragment", "API 반호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: retrofit2.Call<ProgressPageResponse>, t: Throwable) {
                    Log.e("FileFragment", "API 완전호출 실패", t)
                }
            })
        }

        // 화면 시작시에 처음 보여야되는 리사이클러뷰 설정
        isFileIcon1Selected = true
        isFileIcon2Selected = false
        binding.fileIcon1.isSelected = true
        binding.fileIcon2.isSelected = false
        binding.fileIcon1.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.file_btn_focus)
        binding.fileIcon2.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.file_btn_unfocus)

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.file_frame,Gh_List_Icon1_Fragment())
            .commit()

        //mainActivity!!.openFragment(2)

        // =버튼 클릭 시 색상 변경 관련
        binding.fileIcon1.setOnClickListener {
            if (!isFileIcon1Selected) {
                isFileIcon1Selected = true
                isFileIcon2Selected = false
                binding.fileIcon1.isSelected = true
                binding.fileIcon2.isSelected = false
                binding.fileIcon1.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.file_btn_focus)
                binding.fileIcon2.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.file_btn_unfocus)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.file_frame,Gh_List_Icon1_Fragment())
                    .addToBackStack(null)
                    .commit()
                //mainActivity!!.openFragment(2)
            }
        }

        // ㅁ버튼 클릭 시 색상 변경 관련
        binding.fileIcon2.setOnClickListener {
            if (!isFileIcon2Selected) {
                isFileIcon1Selected = false
                isFileIcon2Selected = true
                binding.fileIcon1.isSelected = false
                binding.fileIcon2.isSelected = true
                binding.fileIcon1.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.file_btn_unfocus)
                binding.fileIcon2.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.file_btn_focus)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.file_frame,Gh_List_Icon2_Fragment())
                    .addToBackStack(null)
                    .commit()

                //mainActivity!!.openFragment(3)
            }
        }

        binding.btnCreate.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnNew1.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }
}