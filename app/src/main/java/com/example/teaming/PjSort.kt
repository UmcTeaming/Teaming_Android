package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentPjSortBinding

class PjSort : Fragment() {
    private lateinit var binding:FragmentPjSortBinding
    private lateinit var pjOutAdapter: PjOutAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPjSortBinding.inflate(inflater,container,false)

        val dataList = ArrayList<PjOutData>()

        // 아래는 데이터 추가 영역
        val item1 = PjOutData("2023. 06. 20", ArrayList<PjInData>())
        item1.innerList.add(PjInData("oo 교양 조별과제 자료조사 1", 5))
        dataList.add(item1)

        val item2 = PjOutData("2023. 06. 19", ArrayList<PjInData>())
        item2.innerList.add(PjInData("oo 교양 조별과제 자료조사 2", 2))
        item2.innerList.add(PjInData("oo 교양 조별과제 자료조사 1", 1))
        dataList.add(item2)

        val item3 = PjOutData("2023. 06. 18", ArrayList<PjInData>())
        item3.innerList.add(PjInData("oo 교양 발표ppt", 4))
        item3.innerList.add(PjInData("oo 교양 조별과제 자료조사 2", 2))
        item3.innerList.add(PjInData("oo 교양 조별과제 자료조사 1", 1))
        dataList.add(item3)

        val item4 = PjOutData("2023. 06. 17", ArrayList<PjInData>())
        item4.innerList.add(PjInData("oo 교양 발표ppt2", 4))
        item4.innerList.add(PjInData("oo 교양 발표ppt", 4))
        item4.innerList.add(PjInData("oo 교양 조별과제 자료조사 2", 2))
        item4.innerList.add(PjInData("oo 교양 조별과제 자료조사 1", 1))
        dataList.add(item4)
        //

        pjOutAdapter = PjOutAdapter(dataList)
        binding.outRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pjOutAdapter
        }

        return binding.root
    }

}