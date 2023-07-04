package com.example.teaming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teaming.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val verItemList = arrayListOf<VerListItem>()      // 아이템 배열
    private val verAdapter = VerticalAdapter(verItemList)
    private val horItemList = arrayListOf<HorListItem>()
    private val horAdapter = HorizontalAdapter(horItemList)
    private val gridItemList = arrayListOf<GridListItem>()
    private val gridAdapter = GridAdapter(gridItemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater,container,false)

        binding.verList.layoutManager = LinearLayoutManager(context)
        binding.verList.adapter = verAdapter

        binding.horList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.horList.adapter = horAdapter

        binding.gridList.layoutManager = GridLayoutManager(context,3) 
        binding.gridList.adapter = gridAdapter

        // 아이템 추가
        verItemList.add(VerListItem("UMC 파이널 프로젝트(프로젝트명)", "2023.06.13 ~ (진행기간)"))
        verItemList.add(VerListItem("UMC 파이널 프로젝트(프로젝트명)", "2023.06.13 ~ (진행기간)"))
        verItemList.add(VerListItem("UMC 파이널 프로젝트(프로젝트명)", "2023.06.13 ~ (진행기간)"))
        // 리스트가 변경됨을 어댑터에 알림
        verAdapter.notifyDataSetChanged()

        horItemList.add(HorListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트","2023.06.13 ~"))
        horItemList.add(HorListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트","2023.06.13 ~"))
        horItemList.add(HorListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트","2023.06.13 ~"))
        horAdapter.notifyDataSetChanged()

        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","진행일자"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","진행일자"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","진행일자"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","진행일자"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","진행일자"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","진행일자"))
        gridAdapter.notifyDataSetChanged()

        return binding.root
    }
}