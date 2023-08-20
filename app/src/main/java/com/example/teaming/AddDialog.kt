package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teaming.databinding.CalDialogNewBinding
import com.example.teaming.databinding.InviteNoInfoDialogBinding
import com.example.teaming.databinding.InviteYesInfoDialogBinding
import com.example.teaming.databinding.UserAddDialogBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDialog(): DialogFragment() {
    private lateinit var binding: UserAddDialogBinding

    private lateinit var inviteYesInfoDialog: Dialog
    private lateinit var inviteNoInfoDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserAddDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.btnDel.setOnClickListener{
            dismiss()
        }

        binding.btnInvite.setOnClickListener {
        }

//        binding.btnX.setOnClickListener {
//            dismiss()
//        }

        return view
    }
}