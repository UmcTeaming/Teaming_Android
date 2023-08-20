package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.net.Uri
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
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep

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

        var projectId: Int? = null
        var num = arguments?.getInt("num")
        /*var num2 = arguments?.getInt("num2")
        var num3 = arguments?.getInt("num3")
        Log.e("num","${num2}, ${num3}")*/

        /*if(num2!=2){
            projectId = arguments?.getInt("createProjectID")
        }

        if(num3!=3){
            projectId = arguments?.getInt("modiProjectID")
        }

        if(num2==0 && num3==0){
            projectId = arguments?.getInt("projectID")
        }*/

        projectId = when(num){
            2-> arguments?.getInt("modiProjectID")
            3-> arguments?.getInt("createProjectID")
            else-> arguments?.getInt("projectID")
        }

        val preferences = requireContext().getSharedPreferences("projectID_page", AppCompatActivity.MODE_PRIVATE)

        val editor = preferences.edit()
        editor.putInt("projectID_page", projectId ?: -1)

        editor.commit()

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer,PjSort())
            .commit()

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val memberId = sharedPreference.getInt("memberId",-1)

        val callProjectPage = RetrofitApi.getRetrofitService.projectPage(memberId,projectId)

        callProjectPage.enqueue(object : Callback<ProjectpageResponse> {
            override fun onResponse(call: Call<ProjectpageResponse>, response: Response<ProjectpageResponse>) {
                if (response.isSuccessful) {
                    val projectpageresponse = response.body()
                    if (projectpageresponse != null) {

                        binding.projectNameTop.text = projectpageresponse.data.name
                        binding.projectNameBottom.text = projectpageresponse.data.name

                        Glide.with(requireContext())
                            .load(projectpageresponse.data.image)
                            .error(R.drawable.pj_image_default)
                            .fitCenter()
                            .into(binding.pjImage)

                        if (projectpageresponse.data.projectStatus == "ING"){
                            binding.status.setImageResource(R.drawable.circle)
                            binding.projectDate.text = "${projectpageresponse.data.startDate} ~ 진행중"
                        }else{
                            binding.status.setImageResource(R.drawable.circle_end)
                            binding.projectDate.text = "${projectpageresponse.data.startDate} ~ ${projectpageresponse.data.startDate}"
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
                        while (itemList.size < 4) {
                            itemList.add(MemberData("no_profile", "기본"))
                        }

                        val memberboard = binding.root.findViewById<RecyclerView>(R.id.member)

                        val memberAdapter = MemberAdapter(itemList)
                        memberAdapter.notifyDataSetChanged()

                        memberboard.adapter = memberAdapter
                        memberboard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    }
                } else {
                    Log.d("projectpage", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProjectpageResponse>, t: Throwable) {
                Log.e("projectpage", "로그인 API 호출 실패", t)
            }
        })

        binding.pjFile.setOnClickListener {
            setButtonState(true)

            val bundle = Bundle()

            bundle.putString("file_color","project")

            val pjSort = PjSort()
            pjSort.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,pjSort)
                .commit()
        }

        binding.finalFile.setOnClickListener {
            setButtonState(false)

            val bundle = Bundle()

            bundle.putString("file_color","final")

            val fiSort = FiSort()
            fiSort.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,fiSort)
                .commit()
        }

        binding.inviteBtn.setOnClickListener {
            showPjInviteDialog()
        }

        binding.projectSchedules.setOnClickListener{
            val dialog = ProjectScheduleDialog()
            val args = Bundle()
            args.putInt("projectId", projectId!!)
            args.putInt("memberId", memberId!!)
            dialog.arguments = args
            dialog.show(requireActivity().supportFragmentManager,"CalNewScheduleDialog")
        }

        binding.modifyBtn.setOnClickListener{

            val bundle = Bundle()
            bundle.putInt("projectID",projectId!!)

            val modifyFragment = ModifyFragment()
            modifyFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,modifyFragment)
                .addToBackStack(null)
                .commit()
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

            val sharedPreference_mem = requireActivity().getSharedPreferences("memberId",
                Context.MODE_PRIVATE
            )
            val sharedPreference = requireActivity().getSharedPreferences("projectID_page",
                Context.MODE_PRIVATE
            )

            val memberId = sharedPreference_mem.getInt("memberId",-1)

            val projectId = sharedPreference.getInt("projectID_page",-1)

            val requestBodyData = InvitationsRequest(dialogBinding.emailWrite.text.toString())
            Log.d("리퀘스트","${requestBodyData}")
            val json = Gson().toJson(requestBodyData)
            val requestBody = RequestBody.create("application/json".toMediaType(), json)

            val callInvitation = RetrofitApi.getRetrofitService.invitation(memberId,projectId,requestBody)

            callInvitation.enqueue(object : Callback<InvitationsResponse> {
                override fun onResponse(call: Call<InvitationsResponse>, response: Response<InvitationsResponse>) {
                    if (response.isSuccessful) {
                        val invitationsResponse = response.body()
                        if (invitationsResponse != null) {
                            Log.d("Invitation", "API 호출 성공: ${invitationsResponse}")
                            pjInviteDialog.dismiss()
                            showInviteYesInfoDialog()

                            itemList.clear()
                            for (member in invitationsResponse.data.members) {
                                itemList.add(
                                    MemberData(
                                        member.memberImage,
                                        member.memberName
                                    )
                                )
                            }
                            while (itemList.size < 4) {
                                itemList.add(MemberData("no_profile", "기본"))
                            }

                            val memberboard = binding.root.findViewById<RecyclerView>(R.id.member)

                            val memberAdapter = MemberAdapter(itemList)
                            memberAdapter.notifyDataSetChanged()

                            memberboard.adapter = memberAdapter
                            memberboard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                            if(invitationsResponse.status != 200){
                                pjInviteDialog.dismiss()
                                showInviteNoInfoDialog(invitationsResponse.status)
                            }
                        }
                    } else {
                        Log.d("Invitation", "API 호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<InvitationsResponse>, t: Throwable) {
                    Log.e("Invitation", "로그인 API 호출 실패", t)
                }
            })


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

            sleep(300)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,PjPageFragment())
                .commit()

            inviteYesInfoDialog.dismiss()

        }

        inviteYesInfoDialog.show()
    }

    private fun showInviteNoInfoDialog(errorcode : Int) {
        inviteNoInfoDialog = Dialog(requireContext())
        val dialogBinding: InviteNoInfoDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.invite_no_info_dialog,
            null,
            false
        )

        if (errorcode == 208){
            dialogBinding.noWayHome.text = "이미 참여 중인 초대자입니다."
        }else if(errorcode == 404){
            dialogBinding.noWayHome.text = "회원이 아닌 초대자 입니다."
        }
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