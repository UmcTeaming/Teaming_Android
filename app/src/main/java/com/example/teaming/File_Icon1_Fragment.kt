package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentFileBinding
import com.example.teaming.databinding.FragmentFileIcon1Binding

// 하단바 file 화면 내에서 보여지는 리사이클러뷰1 - = 눌렀을 때 보여지는 화면
class File_Icon1_Fragment : Fragment() {
    private val verItemList = arrayListOf<VerListItem>()      // 아이템 배열
    private val verAdapter2 = VerticalAdapter2(verItemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFileIcon1Binding.inflate(inflater,container,false)

        binding.potVerList.layoutManager = LinearLayoutManager(context)
        binding.potVerList.adapter = verAdapter2

        verItemList.clear()
        // 아이템 추가
        for (i: Int in 1..8){
            verItemList.add(VerListItem(R.drawable.state_oval,"UMC 파이널 프로젝트 Teaming", "2023. 07. 01 ~ 2023. 08. 29"))
        }
        verAdapter2.notifyDataSetChanged()


        return binding.root
    }


}