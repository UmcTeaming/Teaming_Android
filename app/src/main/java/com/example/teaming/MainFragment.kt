package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        // API 설정 - GET
        val memberId = arguments?.getInt("memberId")
        Log.d("Id","${memberId}")

        val callMainPage = RetrofitApi.getRetrofitService.mainPage(memberId)

        if(memberId!=null){
            callMainPage.enqueue(object : Callback<MainPageResponse> {
                override fun onResponse(call: Call<MainPageResponse>, response: retrofit2.Response<MainPageResponse>) {
                    if (response.isSuccessful) {
                        val mainPageResponse = response.body()
                        if (mainPageResponse != null) {
                            val recentlyProjects = mainPageResponse.data.recentlyProject
                            Log.d("data", "${recentlyProjects}")

                            horItemList.clear()
                            for (project in recentlyProjects) {
                                horItemList.add(
                                    HorListItem(
                                        R.drawable.file_view_img,
                                        project.projectName,
                                        project.projectCreatedDate
                                    )
                                )
                            }

                            // 아래 두 줄을 추가해서 어댑터에 데이터 변경을 알려줍니다.
                            horAdapter.notifyDataSetChanged()
                            binding.viewPager2.offscreenPageLimit = 3 // ViewPager2 페이지 개수 설정

                            verItemList.clear()
                            val progressProjects = mainPageResponse.data.progressProject
                            for (index in 0 until minOf(progressProjects.size, 3)) {
                                val project = progressProjects[index]
                                verItemList.add(
                                    VerListItem(
                                        R.drawable.state_oval,
                                        project.projectName, // 이 부분 수정 필요
                                        project.projectStartedDate
                                    )
                                )
                            }

                            verAdapter.notifyDataSetChanged()

                            gridItemList.clear()
                            val portfolio = mainPageResponse.data.portfolio
                            for(project in portfolio){
                                val formattedDate = "${project.projectStartDate} ~ ${project.projectEndDate}"
                                gridItemList.add(
                                    GridListItem(
                                        R.drawable.file_background,
                                        project.projectName,
                                        formattedDate
                                    )
                                )
                            }
                            gridAdapter.notifyDataSetChanged()

                            val userId = mainPageResponse.data
                            Log.d("MainFragment", "Data: ${userId}")
                        }
                    } else {
                        Log.e("MainFragment", "API 호출 반 실패: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<MainPageResponse>, t: Throwable) {
                    Log.e("MainFragment", "API 호출 완전 실패", t)
                }
            })
        }

        /*callMainPage.enqueue(object : Callback<MainPageResponse> {
            override fun onResponse(call: Call<MainPageResponse>, response: Response<MainPageResponse>) {
                if (response.isSuccessful) {
                    val mainPageResponse = response.body()
                    if (mainPageResponse != null) {
                        val userId = mainPageResponse.data

                        Log.d("LoginActivity", "Access Token: ${userId}")
                    }
                } else {
                    Log.d("LoginActivity", "API 호출 반 실패: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<MainPageResponse>, t: Throwable) {
                Log.e("MainFragment", "API 호출 완전 실패", t)
            }
        })*/

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

        /*// ver 아이템 초기화
        verItemList.clear()
        // ver 아이템 추가
        verItemList.add(VerListItem(R.drawable.state_oval,"UMC 파이널 프로젝트(프로젝트명)", "2023.06.13 ~ (진행기간)"))
        verItemList.add(VerListItem(R.drawable.state_oval,"UMC 파이널 프로젝트(프로젝트명)", "2023.06.13 ~ (진행기간)"))
        verItemList.add(VerListItem(R.drawable.state_oval,"UMC 파이널 프로젝트(프로젝트명)", "2023.06.13 ~ (진행기간)"))*/

        // 리스트가 변경됨을 어댑터에 알림
        //verAdapter.notifyDataSetChanged()

        //val recentlyProjects = mainPageResponse.data.recentlyProject
        /*horItemList.add(HorListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트","2023.06.13 ~"))
        horItemList.add(HorListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트","2023.06.13 ~"))
        horItemList.add(HorListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트","2023.06.13 ~"))
        horAdapter.notifyDataSetChanged()*/

        /*// grid 아이템 초기화
        gridItemList.clear()
        // grid 아이템 추가
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))
        gridItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"프로젝트명","2023.06.13 ~ (진행기간)"))*/
        //gridAdapter.notifyDataSetChanged()

        binding.btnMainCreate.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .addToBackStack(null)
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
                    .replace(R.id.container,DocRead())
                    .addToBackStack(null)
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
                    .addToBackStack(null)
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
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.btnNew1.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnNew2.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .addToBackStack(null)
                .commit()
        }


        /*
        // 프로젝트가 없는 경우
        binding.nonViewPager2.visibility = View.INVISIBLE
        binding.nonVerList.visibility = View.GONE
        binding.nonGridList.visibility = View.INVISIBLE
        */

        return binding.root
    }
}