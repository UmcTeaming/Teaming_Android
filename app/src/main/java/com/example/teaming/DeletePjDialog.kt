package com.example.teaming

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentDeletePjDialogBinding
import com.example.teaming.databinding.FragmentDialogAgreeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeletePjDialog : DialogFragment() {

    private lateinit var binding: FragmentDeletePjDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDeletePjDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.noDel.setOnClickListener {
            dismiss()
        }

        binding.yesDel.setOnClickListener {
            val memberId = arguments?.getInt("memId")
            val projectId = arguments?.getInt("pjId")
            val num = arguments?.getInt("num")

            val callDelete = RetrofitApi.getRetrofitService.projectDelete(memberId,projectId)

            callDelete.enqueue(object : Callback<DeleteProjectResponse> {
                override fun onResponse(
                    call: Call<DeleteProjectResponse>, response: Response<DeleteProjectResponse>
                ) {
                    if (response.isSuccessful) {
                        val deleteProjectResponse = response.body()
                        if (deleteProjectResponse != null) {
                            Log.d("delte","delete 구현 완료")
                            dismiss()

                            if(num == 1){
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.container,ListFragment())
                                    .addToBackStack(null)
                                    .commit()
                            }
                            else{
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.container,FileFragment())
                                    .addToBackStack(null)
                                    .commit()
                            }
                            //Toast.makeText(this@DeletePjDialog, "프로젝트가 삭제되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("delete", "API 반호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DeleteProjectResponse>, t: Throwable) {
                    Log.e("delete", "API 완전호출 실패", t)
                }
            })
        }

        return view
    }

}