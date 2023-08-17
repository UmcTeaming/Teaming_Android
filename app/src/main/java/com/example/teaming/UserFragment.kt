package com.example.teaming

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.teaming.databinding.ActivityMypageBinding
import com.example.teaming.databinding.FragmentMainBinding
import com.example.teaming.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentUserBinding.inflate(inflater,container,false)

        binding.pencil.setOnClickListener {
            setFragment()
        }

        /*binding.secret.setOnClickListener {
            val intent = Intent(this, ChangeNum::class.java)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.Button_mail);
        button.paintFlags = button.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        button.text = getString(R.string.underlined_text2)*/

        return binding.root
    }

    private fun setFragment() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, NameFragment())
        transaction.commit()
    }
}