package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.CalDialogNewBinding
import com.example.teaming.databinding.PjCompleteDialogBinding
import com.example.teaming.databinding.PjModiDialogBinding
import com.example.teaming.databinding.UserAddDialogBinding

class PjModiDialog(): DialogFragment() {
    private lateinit var binding: PjModiDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PjModiDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        // 확인 버튼
        binding.btnYes.setOnClickListener{
            dismiss()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,MainFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnPj.setOnClickListener{
            val projectId = arguments?.getInt("modiProjectID")
            //val projectId3 = arguments?.getInt("createProjectID")
            val num = arguments?.getInt("num")
            /*val num3 = arguments?.getInt("num")*/

            //Log.e("넘긴 값들","${projectId},${projectId3}, ${num}")

            val bundle = Bundle()
            bundle.putInt("modiProjectID",projectId!!)
            bundle.putInt("num",num!!)
            //bundle.putInt("num",num3!!)

            val pjPageFragment = PjPageFragment()
            pjPageFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,pjPageFragment)
                .addToBackStack(null)
                .commit()
            dismiss()
        }

        return view
    }
}