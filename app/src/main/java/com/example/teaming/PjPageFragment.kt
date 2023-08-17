package com.example.teaming

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teaming.databinding.FragmentPjPageBinding
import com.example.teaming.databinding.InviteNoInfoDialogBinding
import com.example.teaming.databinding.InviteYesInfoDialogBinding
import com.example.teaming.databinding.PjInviteDialogBinding

class PjPageFragment : Fragment() {
    private lateinit var binding:FragmentPjPageBinding

    private lateinit var pjInviteDialog: Dialog
    private lateinit var inviteYesInfoDialog: Dialog
    private lateinit var inviteNoInfoDialog: Dialog

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


        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer,PjSort())
            .commit()

        //이쪽은 멤버 관리 부분 리사이클러뷰 -수정 가능
        val member_board = binding.root.findViewById<RecyclerView>(R.id.member)

        val itemList = ArrayList<MemberData>()

        itemList.add(MemberData(R.drawable.profile_default,"만웅"))
        itemList.add(MemberData(R.drawable.profile_default,"브라운"))
        itemList.add(MemberData(R.drawable.profile_default,"루스"))

        while (itemList.size < 4) {
            itemList.add(MemberData(R.drawable.no_profile, "기본"))
        }

        val memberAdapter = MemberAdapter(itemList)
        memberAdapter.notifyDataSetChanged()

        member_board.adapter = memberAdapter
        member_board.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

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