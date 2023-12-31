package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentFileBinding
import com.example.teaming.databinding.FragmentFileIcon1Binding
import com.example.teaming.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FileFragment : Fragment() {
    private lateinit var binding: FragmentFileBinding
    private var memberName:String? = null

    // 색상 변경을 위해 선택되었는지 아닌지 확인하는 변수
    private var isFileIcon1Selected = true
    private var isFileIcon2Selected = false

    private var searchText:String? = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

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

        binding.searchEditText.text.clear()
        searchText = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFileBinding.inflate(inflater,container,false)

        binding.searchBtn.setOnClickListener{
            searchText = binding.searchEditText.text.toString().trim()

            val bundle = Bundle()
            bundle.putString("searchText", searchText)

            val fileFragment1 = File_Icon1_Fragment()
            fileFragment1.arguments = bundle

            isFileIcon1Selected = true
            isFileIcon2Selected = false
            binding.fileIcon1.isSelected = true
            binding.fileIcon2.isSelected = false
            binding.fileIcon1.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.file_btn_focus)
            binding.fileIcon2.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.file_btn_unfocus)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.file_frame, fileFragment1)
                .addToBackStack(null)
                .commit()
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
            .add(R.id.file_frame,File_Icon1_Fragment())
            .commit()

        /*val sharedPreference = requireActivity().getSharedPreferences("memberName",
            Context.MODE_PRIVATE
        )
        val memberName = sharedPreference.getString("memberName", "카리나")
        binding.memberName.text = memberName

        Log.e("파일프래그, name", "${memberName}")*/

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

                val bundle = Bundle()
                bundle.putString("searchText", searchText)

                val fileFragment1 = File_Icon1_Fragment()
                fileFragment1.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.file_frame,fileFragment1)
                    .addToBackStack(null)
                    .commit()
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

                val bundle = Bundle()
                bundle.putString("searchText", searchText)

                val fileFragment2 = File_Icon2_Fragment()
                fileFragment2.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.file_frame,fileFragment2)
                    .addToBackStack(null)
                    .commit()
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

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val memberId = sharedPreference.getInt("memberId",-1)
        Log.e("포트폴리오 id","${memberId}")

        val callPortfolioPage = RetrofitApi.getRetrofitService.portfolioPage(memberId)

        if(memberId!=null){
            callPortfolioPage.enqueue(object : Callback<PortfolioPageResponse> {
                override fun onResponse(
                    call: Call<PortfolioPageResponse>, response: Response<PortfolioPageResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e("포트폴리오 memberId","${memberId}")
                        val portfolioPageResponse = response.body()
                        if (portfolioPageResponse != null) {
                            val portfolioProjects = portfolioPageResponse.data.member_name
                            binding.memberName.text = portfolioProjects
                            Log.e("List 사용자명","${portfolioProjects}")

                        }
                    } else {
                        Log.d("FileFragment", "API 반호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PortfolioPageResponse>, t: Throwable) {
                    Log.e("FileFragment", "API 완전호출 실패", t)
                }
            })
        }


        return binding.root
    }
}