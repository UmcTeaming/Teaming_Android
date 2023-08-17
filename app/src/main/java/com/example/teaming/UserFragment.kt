package com.example.teaming


import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.teaming.databinding.FragmentUserBinding

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