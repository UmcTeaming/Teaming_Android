package com.example.teaming

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.CalDialogNewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class CalNewScheduleDialog(private val parent:ProjectScheduleDialog):DialogFragment() {
    private lateinit var binding:CalDialogNewBinding
    private var focus:Int?=null //1->시작 일자 2->종료일자 3->시작시간 4->종료시간
    val decimalForm = DecimalFormat("00")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalDialogNewBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.makeSchedule.setOnClickListener{
            val inputFormat = SimpleDateFormat("yyyy. MM. dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val inputTimeFormat = DateTimeFormatter.ofPattern("HH : mm")
            val outputTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss")
            val startDayBefore: Date = inputFormat.parse(binding.scheduleStartDay.text.toString())
            val endDayBefore: Date = inputFormat.parse(binding.scheduleEndDay.text.toString())
            val startDayAfter: String = outputFormat.format(startDayBefore)
            val endDayAfter : String = outputFormat.format(endDayBefore)
            val startTimeBefore: LocalTime = LocalTime.parse(binding.scheduleStartTime.text.toString(), inputTimeFormat)
            val startTimeAfter: String = startTimeBefore.format(outputTimeFormat)
            val endTimeBefore: LocalTime = LocalTime.parse(binding.scheduleEndTime.text.toString(), inputTimeFormat)
            val endTimeAfter: String = endTimeBefore.format(outputTimeFormat)
            //Log.d("chanho", "scheduleName: " + binding.scheduleName.text.toString() + " " +  startDayAfter + " " + endDayAfter + " " + startTimeAfter + " " + endTimeAfter)
            val args = getArguments()
            val memberId = args!!.getInt("memberId")
            val projectId = args!!.getInt("projectId")
            val req = CreateSchedule(binding.scheduleName.text.toString(), startDayAfter, endDayAfter, startTimeAfter, endTimeAfter)
            val retrofitObj = RetrofitApi.getRetrofitService.createSchedule(memberId,projectId,req)
            retrofitObj.enqueue(object :Callback<CreateSchedule>{
                override fun onResponse(
                    call: Call<CreateSchedule>,
                    response: Response<CreateSchedule>
                ) {
                    if (response.isSuccessful){
                        Log.d("chanho", "Success")
                        val dialog = ScheduleMakeCompleteDialog()
                        val args = Bundle()
                        args.putInt("projectId", 11)
                        dialog.arguments = args
                        dialog.show(requireActivity().supportFragmentManager,"ScheduleMakeCompleteDialog")
                        parent.updateAdapter()
                        dismiss()
                    }
                    else
                        Log.d("chanho", "not success")
                }
                override fun onFailure(call: Call<CreateSchedule>, t: Throwable) {
                    Log.d("chanho", "onFailure")
                }

            })

            //여기에 등록로직추가해야.
        }
        binding.toBefore.setOnClickListener{
            dismiss()
        }
        binding.scheduleStartDay.setOnClickListener{
            binding.scheduleStartDay.setTextColor(Color.BLACK)
            binding.scheduleEndDay.setTextColor(Color.GRAY)
            binding.scheduleStartTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            binding.scheduleEndTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            binding.scheduleTimePicker.visibility=View.GONE
            binding.scheduleDatePicker.visibility=View.VISIBLE
            focus = 1
        }
        binding.scheduleEndDay.setOnClickListener{
            binding.scheduleStartDay.setTextColor(Color.GRAY)
            binding.scheduleEndDay.setTextColor(Color.BLACK)
            binding.scheduleStartTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            binding.scheduleEndTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            binding.scheduleTimePicker.visibility=View.GONE
            binding.scheduleDatePicker.visibility=View.VISIBLE
            focus = 2
        }
        binding.scheduleStartTime.setOnClickListener{
            binding.scheduleStartDay.setTextColor(Color.GRAY)
            binding.scheduleEndDay.setTextColor(Color.GRAY)
            binding.scheduleStartTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.cal_day_name_color))
            binding.scheduleEndTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            binding.scheduleDatePicker.visibility=View.GONE
            binding.scheduleTimePicker.visibility = View.VISIBLE
            focus = 3
        }
        binding.scheduleEndTime.setOnClickListener{
            binding.scheduleStartDay.setTextColor(Color.GRAY)
            binding.scheduleEndDay.setTextColor(Color.GRAY)
            binding.scheduleStartTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            binding.scheduleEndTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.cal_day_name_color))
            binding.scheduleDatePicker.visibility=View.GONE
            binding.scheduleTimePicker.visibility = View.VISIBLE
            focus = 4
        }
        binding.scheduleDatePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            if(focus==1){
                binding.scheduleStartDay.text=year.toString()+". "+decimalForm.format(monthOfYear + 1) + ". " + decimalForm.format(dayOfMonth)
            }
            else if(focus==2){
                binding.scheduleEndDay.text=year.toString()+". "+decimalForm.format(monthOfYear + 1) + ". " + decimalForm.format(dayOfMonth)
            }
        }
        binding.scheduleTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            if(focus==3){
                binding.scheduleStartTime.text = decimalForm.format(hourOfDay) + " : " + decimalForm.format(minute)
            }
            else if(focus==4){
                binding.scheduleEndTime.text = decimalForm.format(hourOfDay) + " : " + decimalForm.format(minute)
            }
        }
        
        return view
    }
}