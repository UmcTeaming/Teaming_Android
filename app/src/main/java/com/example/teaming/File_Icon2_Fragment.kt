package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentFileIcon1Binding
import com.example.teaming.databinding.FragmentFileIcon2Binding

// 하단바 file 화면 내에서 보여지는 리사이클러뷰1 - ㅁ 눌렀을 때 보여지는 화면
class File_Icon2_Fragment : Fragment() {
    private val fileVerItemList = arrayListOf<GridListItem>()      // 아이템 배열
    private val fileVerAdapter = FileVerAdapter(fileVerItemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFileIcon2Binding.inflate(inflater,container,false)

        binding.potVerList2.layoutManager = LinearLayoutManager(context)
        binding.potVerList2.adapter = fileVerAdapter

        fileVerItemList.clear()
        // 아이템 추가
        for (i: Int in 1..8){
            fileVerItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트 Teaming", "2023. 07. 01 ~ 2023. 08. 29"))
        }
        fileVerAdapter.notifyDataSetChanged()

        // file페이지 = 버튼 선택시 등장하는 리사이클러뷰 클릭이벤트
        fileVerAdapter.setItemClickListener(object: FileVerAdapter.OnItemClickListener{
            override fun onClick(v:View,position:Int){
                // 클릭 시 이벤트 작성
                Toast.makeText(view?.context,
                    "${position}\n${fileVerItemList[position].grid_title}\n${fileVerItemList[position].grid_date}",
                    Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

}