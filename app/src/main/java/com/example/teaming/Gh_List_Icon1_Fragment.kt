package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentFileIcon1Binding
import com.example.teaming.databinding.FragmentGhListIcon1Binding
import com.example.teaming.databinding.FragmentList1Binding

class Gh_List_Icon1_Fragment : Fragment() {
    private val listItemList = arrayListOf<VerListItem>()      // 아이템 배열
    private val verAdapter2 = VerticalAdapter2(listItemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentGhListIcon1Binding.inflate(inflater,container,false)

        binding.listVerList.layoutManager = LinearLayoutManager(context)
        binding.listVerList.adapter = verAdapter2

        listItemList.clear()
        // 아이템 추가
        for (i: Int in 1..8){
            listItemList.add(VerListItem(R.drawable.state_oval,"UMC 파이널 프로젝트 Teaming", "2023. 07. 01 ~ 2023. 08. 29"))
        }
        verAdapter2.notifyDataSetChanged()

        // file페이지 = 버튼 선택시 등장하는 리사이클러뷰 클릭이벤트
        verAdapter2.setItemClickListener(object: VerticalAdapter2.OnItemClickListener{
            override fun onClick(v:View,position:Int){
                // 클릭 시 이벤트 작성
                /*Toast.makeText(view?.context,
                    "${position}\n${verItemList[position].ver_title}\n${verItemList[position].ver_date}",
                    Toast.LENGTH_SHORT).show()*/

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container,PjPageFragment())
                    .commit()
            }
        })

        return binding.root
    }

}