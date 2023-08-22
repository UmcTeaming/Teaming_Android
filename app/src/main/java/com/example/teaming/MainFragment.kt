package com.example.teaming

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.teaming.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback

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
        /*val memberId = arguments?.getInt("memberId")
        Log.d("R_MainFragment","ID: ${memberId}")*/

        val sharedPreference = requireActivity().getSharedPreferences("memberId", MODE_PRIVATE)
        val memberId = sharedPreference.getInt("memberId", -1)

        val callMainPage = RetrofitApi.getRetrofitService.mainPage(memberId)

        if(memberId!=null){
            callMainPage.enqueue(object : Callback<MainPageResponse> {
                override fun onResponse(call: Call<MainPageResponse>, response: retrofit2.Response<MainPageResponse>) {
                    if (response.isSuccessful) {
                        val mainPageResponse = response.body()
                        if (mainPageResponse != null) {
                            val name = mainPageResponse.data.name
                            binding.memberName.text = name
                            binding.memberName2.text = name

                            val recentlyProjects = mainPageResponse.data.recentlyProject
                            //Log.d("data", "${recentlyProjects}")

                            if(recentlyProjects!=null && recentlyProjects.isNotEmpty()){

                                binding.nonViewPager2.visibility = View.INVISIBLE
                                binding.viewPager2.visibility = View.VISIBLE

                                /*for (project in recentlyProjects) {
                                    Glide.with(requireContext())
                                        .load(project.projectImage)
                                        .error(R.drawable.file_background)
                                        .into(binding.pjImage)
                                }*/

                                horItemList.clear()
                                for (project in recentlyProjects) {
                                    horItemList.add(
                                        HorListItem(
                                            project.projectImage,
                                            project.projectName,
                                            project.projectCreatedDate,
                                            project.projectId,
                                            project.projectStatus
                                        )
                                    )
                                }
                            }
                            else{
                                binding.nonViewPager2.visibility = View.VISIBLE
                                binding.viewPager2.visibility = View.INVISIBLE
                            }

                            // 아래 두 줄을 추가해서 어댑터에 데이터 변경을 알려줍니다.
                            horAdapter.notifyDataSetChanged()
                            binding.viewPager2.offscreenPageLimit = 3 // ViewPager2 페이지 개수 설정

                            verItemList.clear()
                            val progressProjects = mainPageResponse.data.progressProject
                            if(progressProjects!=null && progressProjects.isNotEmpty()){
                                binding.nonVerList.visibility = View.GONE
                                binding.verList.visibility = View.VISIBLE
                                for (index in 0 until minOf(progressProjects.size, 3)) {
                                    val project = progressProjects[index]
                                    val formattedDate = "${project.projectStartDate} ~ "
                                    verItemList.add(
                                        VerListItem(
                                            project.projectName, // 이 부분 수정 필요
                                            formattedDate,
                                            project.projectId,
                                            project.projectStatus
                                        )
                                    )
                                }
                            }
                            else{
                                binding.nonVerList.visibility = View.VISIBLE
                                binding.verList.visibility = View.GONE
                            }
                            verAdapter.notifyDataSetChanged()


                            val portfolio = mainPageResponse.data.portfolio
                            binding.nonGridList.visibility = View.VISIBLE
                            binding.gridList.visibility = View.GONE

                            if(portfolio != null && portfolio.isNotEmpty()){
                                gridItemList.clear()
                                binding.nonGridList.visibility = View.GONE
                                binding.gridList.visibility = View.VISIBLE
                                for(project in portfolio){
                                    val formattedDate = "${project.projectStartDate} ~ ${project.projectEndDate}"
                                    gridItemList.add(
                                        GridListItem(
                                            project.projectImage,
                                            project.projectName,
                                            formattedDate,
                                            project.projectId,
                                            project.projectStatus
                                        )
                                    )
                                }
                            }
                            else{
                                binding.nonGridList.visibility = View.VISIBLE
                                binding.gridList.visibility = View.GONE
                            }
                            gridAdapter.notifyDataSetChanged()

                            val userId = mainPageResponse.data

                            binding.viewPager2.adapter = horAdapter
                            binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                            binding.viewPager2.offscreenPageLimit = 4
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

                            binding.gridList.layoutManager = GridLayoutManager(context,2)
                            binding.gridList.adapter = gridAdapter

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
                                    val bundle = Bundle()

                                    bundle.putInt("projectID",horItemList[position].hor_id)

                                    val pjPageFragment = PjPageFragment()
                                    pjPageFragment.arguments = bundle

                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.container,pjPageFragment)
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

                                    val bundle = Bundle()

                                    bundle.putInt("projectID",verItemList[position].ver_Id)

                                    val pjPageFragment = PjPageFragment()
                                    pjPageFragment.arguments = bundle

                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.container,pjPageFragment)
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

                                    val bundle = Bundle()

                                    bundle.putInt("projectID",gridItemList[position].grid_Id)

                                    val pjPageFragment = PjPageFragment()
                                    pjPageFragment.arguments = bundle

                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.container,pjPageFragment)
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

                            Log.d("R_MainFragment", "Data: ${userId}")
                            Log.d("MainFragment", "Data: ${userId}")
                        }
                       /* if(mainPageResponse == null){
                            // 프로젝트가 없는 경우 해당 화면을 보이도록
                            binding.nonGridList.visibility = View.VISIBLE
                            binding.nonVerList.visibility = View.VISIBLE
                            binding.nonViewPager2.visibility = View.VISIBLE
                            binding.viewPager2.visibility = View.INVISIBLE
                            binding.verList.visibility = View.GONE
                            binding.gridList.visibility = View.INVISIBLE
                        }*/
                    } else {
                        Log.e("R_MainFragment", "API 호출 반 실패: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<MainPageResponse>, t: Throwable) {
                    Log.e("R_MainFragment", "API 호출 완전 실패", t)
                }
            })
        }
        return binding.root
    }
}