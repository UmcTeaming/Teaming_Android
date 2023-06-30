package com.example.teaming

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.teaming.databinding.FragmentCalBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CalFragment : Fragment() {//minsdk API26 이상으로 바꿀 필요 있음
    private lateinit var binding:FragmentCalBinding
    lateinit var selectedDate: LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedDate = LocalDate.now()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCalBinding.inflate(inflater,container,false)
        setMonthView()
        //이전 달 버튼
        binding.leftButton.setOnClickListener{
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }
        //다음 달 버튼
        binding.rightButton.setOnClickListener{
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun monthFromDate(date:LocalDate):String{
        var formatter = DateTimeFormatter.ofPattern("MMM")
        return date.format(formatter)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setMonthView(){
        binding.monthText.text=monthFromDate(selectedDate)
    }


}