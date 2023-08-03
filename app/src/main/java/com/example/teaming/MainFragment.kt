package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater,container,false)

        //viewPager 관련 내용
        binding.viewPager2.adapter = horAdapter
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.currentItem = 1

        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        binding.viewPager2.setPageTransformer { page, position ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            if (position < -1) {
                page.setTranslationX(-myOffset)
            } else if (position <= 1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.04285715f))
                page.setTranslationX(myOffset)
                page.setScaleY(scaleFactor)
                page.setScaleX(scaleFactor)
                page.setAlpha(scaleFactor)
            } else {
                // 기본 값
                //page.setAlpha(0f)
                page.setAlpha(1f)
                page.setTranslationX(myOffset)
            }
        }

        binding.verList.layoutManager = LinearLayoutManager(context)
        binding.verList.adapter = verAdapter

//        binding.horList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        binding.horList.adapter = horAdapter

        binding.gridList.layoutManager = GridLayoutManager(context,2)
        binding.gridList.adapter = gridAdapter

        // 아이템 추가
        verItemList.add(VerListItem(R.drawable.state_oval,"UMC 파이널 프로젝트(프로젝트명)", "2023.06.13 ~ (진행기간)"))
        verItemList.add(VerListItem(R.drawable.state_oval,"UMC 파이널 프로젝트(프로젝트명)", "2023.06.13 ~ (진행기간)"))
        verItemList.add(VerListItem(R.drawable.state_oval,"UMC 파이널 프로젝트(프로젝트명)", "2023.06.13 ~ (진행기간)"))
        // 리스트가 변경됨을 어댑터에 알림
        verAdapter.notifyDataSetChanged()

        horItemList.add(HorListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트","2023.06.13 ~"))
        horItemList.add(HorListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트","2023.06.13 ~"))
        horItemList.add(HorListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트","2023.06.13 ~"))
        horAdapter.notifyDataSetChanged()

        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridAdapter.notifyDataSetChanged()

        binding.btnMainCreate.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .commit()
        }

        // main페이지의 첫번째 가로 리사이클러뷰 클릭이벤트
        horAdapter.setItemClickListener(object: HorizontalAdapter.OnItemClickListener{
            override fun onClick(v:View,position:Int){
                // 클릭 시 이벤트 작성
                /*Toast.makeText(view?.context,
                    "${position}\n${horItemList[position].hor_title}\n${horItemList[position].hor_date}",
                    Toast.LENGTH_SHORT).show()*/

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container,PjPageFragment())
                    .commit()
            }
        })

        // main페이지의 두번째 세로 리사이클러뷰 클릭이벤트
        verAdapter.setItemClickListener(object: VerticalAdapter.OnItemClickListener{
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

        // main페이지의 세번째 그리드 리사이클러뷰 클릭이벤트
        gridAdapter.setItemClickListener(object: GridAdapter.OnItemClickListener{
            override fun onClick(v:View,position:Int){
                // 클릭 시 이벤트 작성
                /*Toast.makeText(view?.context,
                    "${position}\n${gridItemList[position].grid_title}\n${gridItemList[position].grid_date}",
                    Toast.LENGTH_SHORT).show()*/

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container,PjPageFragment())
                    .commit()
            }
        })

        return binding.root
    }
}