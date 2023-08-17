package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teaming.databinding.FragmentPjPageBinding
import com.example.teaming.databinding.InviteNoInfoDialogBinding
import com.example.teaming.databinding.InviteYesInfoDialogBinding
import com.example.teaming.databinding.PjInviteDialogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PjPageFragment : Fragment() {
    private lateinit var binding:FragmentPjPageBinding

    private lateinit var pjInviteDialog: Dialog
    private lateinit var inviteYesInfoDialog: Dialog
    private lateinit var inviteNoInfoDialog: Dialog

    private val itemList = ArrayList<MemberData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPjPageBinding.inflate(inflater,container,false)

        val projectId = arguments?.getInt("projectID")

        Log.d("플젝ID", "$projectId")

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer,PjSort())
            .commit()

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val memberId = sharedPreference.getInt("memberId",-1)

        val callProjectPage = RetrofitApi.getRetrofitService.projectpage(memberId,projectId)

        callProjectPage.enqueue(object : Callback<ProjectpageResponse> {
            override fun onResponse(call: Call<ProjectpageResponse>, response: Response<ProjectpageResponse>) {
                if (response.isSuccessful) {
                    val projectpageresponse = response.body()
                    if (projectpageresponse != null) {

                        binding.projectNameTop.text = projectpageresponse.data.name
                        binding.projectNameBottom.text = projectpageresponse.data.name

                        binding.projectDate.text = "${projectpageresponse.data.startDate} ~ ${projectpageresponse.data.startDate}"

                        Glide.with(requireContext())
                            .load(projectpageresponse.data.image)
                            .error(R.drawable.pj_image_default)
                            .into(binding.pjImage)

                        if (projectpageresponse.data.projectStatus == "ING"){
                            binding.status.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.status_end))
                        }else{
                            binding.status.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.status_ing))
                        }

                        itemList.clear()
                        for (member in projectpageresponse.data.memberList) {
                            itemList.add(
                                MemberData(
                                    member.member_image,
                                    member.member_name
                                )
                            )
                        }


                        Log.d("projectpage", "${projectpageresponse.data.memberList}")
                    }
                } else {
                    Log.d("projectpage", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProjectpageResponse>, t: Throwable) {
                Log.e("projectpage", "로그인 API 호출 실패", t)
            }
        })


        //이쪽은 멤버 관리 부분 리사이클러뷰 - 수정 가능
        val memberboard = binding.root.findViewById<RecyclerView>(R.id.member)

        while (itemList.size < 4) {
            itemList.add(MemberData(null, "기본"))
        }


        val memberAdapter = MemberAdapter(itemList)
        memberAdapter.notifyDataSetChanged()

        memberboard.adapter = memberAdapter
        memberboard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.pjFile.setOnClickListener {
            setButtonState(true)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,PjSort())
                .commit()
        }

        binding.finalFile.setOnClickListener {
            setButtonState(false)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,FiSort())
                .commit()
        }

        binding.inviteBtn.setOnClickListener {
            showPjInviteDialog()
        }

        binding.projectSchedules.setOnClickListener{
            val dialog = ProjectScheduleDialog()
            val args = Bundle()
            //memberID = 54
            //projectID = 11
            args.putInt("projectId", 11)
            args.putInt("memberId", 54)
            dialog.arguments = args
            dialog.show(requireActivity().supportFragmentManager,"CalNewScheduleDialog")
        }

        return binding.root
    }

    private fun setButtonState(default_btn: Boolean) {
        if (default_btn) {
            binding.uploadBtn.apply {
                setImageResource(R.drawable.file_upload_btn)
            }

            binding.pjFile.apply {
                setBackgroundResource(R.drawable.slide_btn_selected)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                requestLayout()
            }

            binding.finalFile.apply {
                setBackgroundResource(0)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.slide_gray))
                requestLayout()
            }

        } else {

            binding.uploadBtn.apply {
                setImageResource(R.drawable.final_upload_btn)
            }

            binding.pjFile.apply {
                setBackgroundResource(0)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.slide_gray))
                requestLayout()
            }

            binding.finalFile.apply {
                setBackgroundResource(R.drawable.slide_btn_selected)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                requestLayout()
            }
        }
    }

    private fun showPjInviteDialog() {
        pjInviteDialog = Dialog(requireContext())
        val dialogBinding: PjInviteDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.pj_invite_dialog,
            null,
            false
        )
        pjInviteDialog.setContentView(dialogBinding.root)

        dialogBinding.cancelChkButton.setOnClickListener {
            pjInviteDialog.dismiss()
        }

        dialogBinding.inviteChkBtn.setOnClickListener {
            if (dialogBinding.emailWrite.text.toString() == "admin@teaming.com") {
                pjInviteDialog.dismiss()
                showInviteYesInfoDialog()
            } else {
                pjInviteDialog.dismiss()
                showInviteNoInfoDialog()
            }
        }

        pjInviteDialog.show()
    }

    private fun showInviteYesInfoDialog() {
        inviteYesInfoDialog = Dialog(requireContext())
        val dialogBinding: InviteYesInfoDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.invite_yes_info_dialog,
            null,
            false
        )
        inviteYesInfoDialog.setContentView(dialogBinding.root)

        dialogBinding.closeYesBtn.setOnClickListener {
            inviteYesInfoDialog.dismiss()
        }

        inviteYesInfoDialog.show()
    }

    private fun showInviteNoInfoDialog() {
        inviteNoInfoDialog = Dialog(requireContext())
        val dialogBinding: InviteNoInfoDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.invite_no_info_dialog,
            null,
            false
        )
        inviteNoInfoDialog.setContentView(dialogBinding.root)

        dialogBinding.yesBtn.setOnClickListener {
            inviteNoInfoDialog.dismiss()
        }

        dialogBinding.reWriteBtn.setOnClickListener {
            inviteNoInfoDialog.dismiss()
            showPjInviteDialog()
        }

        inviteNoInfoDialog.show()
    }
}