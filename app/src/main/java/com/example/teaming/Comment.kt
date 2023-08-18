package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentCommentBinding
import com.example.teaming.databinding.FragmentDocReadBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Comment : Fragment() {
    private lateinit var binding: FragmentCommentBinding

    private val dataList : ArrayList<CommentData> = ArrayList()
    private lateinit var commentAdapter: CommentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(inflater,container,false)

        val sharedPreference_mem = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )

        val fileId = arguments?.getInt("file_id")

        val memberId = sharedPreference_mem.getInt("memberId",-1)

        val callCommentLoadResponse= RetrofitApi.getRetrofitService.commentLoad(memberId,fileId)

        callCommentLoadResponse.enqueue(object : Callback<CommentLoadResponse> {
            override fun onResponse(call: Call<CommentLoadResponse>, response: Response<CommentLoadResponse>) {
                if (response.isSuccessful) {
                    val commentLoadResponse = response.body()
                    if (commentLoadResponse != null && commentLoadResponse.data != null) {
                        Log.d("코멘트","$commentLoadResponse")
                        dataList.addAll(commentLoadResponse.data)

                        commentAdapter = CommentAdapter(dataList)

                        binding.commentRecycler.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = commentAdapter
                        }
                    }
                } else {
                    Log.d("comment_load", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CommentLoadResponse>, t: Throwable) {
                Log.e("comment_load", "로그인 API 호출 실패", t)
            }
        })


        binding.sendBtn.setOnClickListener{
            val requestBodyData = CommentWriteRequest(binding.inputComment.text.toString())
            val json = Gson().toJson(requestBodyData)
            val requestBody = RequestBody.create("application/json".toMediaType(), json)

            val callCommentWriteResponse= RetrofitApi.getRetrofitService.commentWrite(memberId,fileId,requestBody)

            callCommentWriteResponse.enqueue(object : Callback<CommentWriteResponse> {
                override fun onResponse(call: Call<CommentWriteResponse>, response: Response<CommentWriteResponse>) {
                    if (response.isSuccessful) {
                        val commentWriteResponse = response.body()
                        if (commentWriteResponse != null) {
                            Log.d("코멘트","$commentWriteResponse")

                            loadComments()
                            binding.inputComment.text.clear()

                        }
                    } else {
                        Log.d("comment_write", "API 호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CommentWriteResponse>, t: Throwable) {
                    Log.e("comment_write", "로그인 API 호출 실패", t)
                }
            })
        }


        return binding.root
    }

    private fun loadComments() {

        dataList.clear()

        val sharedPreference_mem = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )

        val fileId = arguments?.getInt("file_id")

        val memberId = sharedPreference_mem.getInt("memberId",-1)

        val callCommentLoadResponse = RetrofitApi.getRetrofitService.commentLoad(memberId, fileId)

        callCommentLoadResponse.enqueue(object : Callback<CommentLoadResponse> {
            override fun onResponse(
                call: Call<CommentLoadResponse>,
                response: Response<CommentLoadResponse>
            ) {
                if (response.isSuccessful) {
                    val commentLoadResponse = response.body()
                    if (commentLoadResponse != null && commentLoadResponse.data != null) {
                        Log.d("코멘트", "$commentLoadResponse")
                        dataList.addAll(commentLoadResponse.data)
                        commentAdapter.notifyDataSetChanged()
                    }
                } else {
                    Log.d("comment_load", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CommentLoadResponse>, t: Throwable) {
                Log.e("comment_load", "로그인 API 호출 실패", t)
            }
        })
    }
}
