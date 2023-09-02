package com.example.teaming

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.teaming.databinding.FragmentCreateBinding
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.lang.Thread.sleep
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateFragment : Fragment(), ColSelDialog.OnColorSelectedListener, ImgDialog.OnImgSelectedListener {
    private lateinit var binding: FragmentCreateBinding
    private var selectedImageUri: Uri? = null
    private var focus:Int?=null //1->시작 일자 2->종료일자 3->시작시간 4->종료시간
    val decimalForm = DecimalFormat("00")
    var startDaySet= false
    var endDaySet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateBinding.inflate(inflater,container,false)

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )

        val memberId = sharedPreference.getInt("memberId",-1)
        Log.e("프로젝트 생성시 memberId","${memberId}")

        binding.imgAdd.setOnClickListener{
            val imgDialog = ImgDialog()
            imgDialog.show(requireActivity().supportFragmentManager,"ImgDialog")
            // target설정을 해야 interface사용이 가능하다고 함
            imgDialog.setTargetFragment(this,1)
        }

        binding.btnSelColor.setOnClickListener {
            val colSelDialog = ColSelDialog()
            colSelDialog.show(requireActivity().supportFragmentManager,"ColSelDialog")
            // target설정을 해야 interface사용이 가능하다고 함
            colSelDialog.setTargetFragment(this,0)

        }

        binding.pjStart.setOnClickListener{
            binding.pjStart.setTextColor(Color.BLACK)
            binding.pjEnd.setTextColor(Color.GRAY)
            //binding.scheduleStartTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            //binding.scheduleEndTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            binding.cDatePicker.visibility=View.VISIBLE
            //binding.scheduleDatePicker.visibility=View.VISIBLE

            focus = 1
        }
        binding.pjEnd.setOnClickListener{
            binding.pjStart.setTextColor(Color.GRAY)
            binding.pjEnd.setTextColor(Color.BLACK)
            //binding.scheduleStartTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            //binding.scheduleEndTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.disabled))
            binding.cDatePicker.visibility=View.VISIBLE
            //binding.scheduleDatePicker.visibility=View.VISIBLE
            focus = 2
        }

        binding.cDatePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            if(focus==1){
                binding.pjStart.text =year.toString()+". "+decimalForm.format(monthOfYear + 1) + ". " + decimalForm.format(dayOfMonth)
                startDaySet = true
                /*if(startDaySet && endDaySet) {
                    binding.cDatePicker.visibility = View.GONE
                }*/
               /* if (startTimeSet && endTimeSet && startDaySet && endDaySet){
                    binding.makeSchedule.isEnabled = true
                    binding.makeSchedule.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.cal_day_name_color))
                }*/
            }
            else if(focus==2){
                binding.pjEnd.text = year.toString()+". "+decimalForm.format(monthOfYear + 1) + ". " + decimalForm.format(dayOfMonth)
                endDaySet = true

                /*if(startDaySet && endDaySet) {
                    binding.cDatePicker.visibility = View.GONE
                }*/
                /*if (startTimeSet && endTimeSet && startDaySet && endDaySet){
                    binding.makeSchedule.isEnabled = true
                    binding.makeSchedule.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.cal_day_name_color))
                }*/
            }

            val handler = Handler()
            handler.postDelayed({
                if (startDaySet || endDaySet) {
                    binding.cDatePicker.visibility = View.GONE
                }
            }, 800) // 1000ms (1초) 후에 View.GONE으로 설정
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.pjName.addTextChangedListener(textWatcher)
        binding.pjStart.addTextChangedListener(textWatcher)
        binding.pjEnd.addTextChangedListener(textWatcher)

        updateButtonState()

        binding.btnCreateProject.setOnClickListener {
            val name = binding.pjName.text.toString()
            /*val start = binding.pjStart.text.toString()
            val end = binding.pjEnd.text.toString()*/
            val backgroundColor = binding.createCol.backgroundTintList?.defaultColor ?: 0
            val hexColor = String.format("#%06X", 0xFFFFFF and backgroundColor)

            val inputFormat = SimpleDateFormat("yyyy. MM. dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val start: Date = inputFormat.parse(binding.pjStart.text.toString())
            val end: Date = inputFormat.parse(binding.pjEnd.text.toString())
            val startDayAfter: String = outputFormat.format(start)
            val endDayAfter : String = outputFormat.format(end)

            val imagePart: MultipartBody.Part? = selectedImageUri?.let { uri ->
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val imageByteArray = inputStream?.readBytes()

                imageByteArray?.let {
                    val imageRequestBody = createBitmapRequestBody(BitmapFactory.decodeByteArray(it, 0, it.size))
                    MultipartBody.Part.createFormData("project_image", uri.lastPathSegment, imageRequestBody)
                }
            }

            val nameRequestBody: RequestBody = RequestBody.create("text/plain".toMediaType(), name)
            val startRequestBody: RequestBody = RequestBody.create("text/plain".toMediaType(), startDayAfter)
            val endRequestBody: RequestBody = RequestBody.create("text/plain".toMediaType(), endDayAfter)
            val hexColorRequestBody: RequestBody = RequestBody.create("text/plain".toMediaType(), hexColor)

            val textHashMap = hashMapOf<String, RequestBody>()
            textHashMap["project_name"] = nameRequestBody
            textHashMap["start_date"] = startRequestBody
            textHashMap["end_date"] = endRequestBody
            textHashMap["project_color"] = hexColorRequestBody

            val call = RetrofitApi.getRetrofitService.createProject(
                memberId,
                projectImage = imagePart!!,
                textHashMap
            )

            call.enqueue(object : Callback<CreateProjectResponse> {
                override fun onResponse(call: Call<CreateProjectResponse>, response: Response<CreateProjectResponse>) {
                    if (response.isSuccessful) {
                        val createProjectResponse = response.body()
                        if (createProjectResponse != null) {
                            val projectId = createProjectResponse.data.project_id
                            if (projectId != null) {
                                Log.e("Post 여부", "Post 성공: 프로젝트 ID = $projectId")

                                val bundle = Bundle()
                                bundle.putInt("createProjectID",projectId!!)
                                bundle.putInt("num",3)

                                val dialog = PjCompleteDialog()
                                dialog.arguments = bundle

                                // Show the success dialog or perform other actions
                                dialog.show(requireActivity().supportFragmentManager, "PjCompleteDialog")
                            } else {
                                Log.e("Post 여부", "Post 성공하지만 프로젝트 ID가 없습니다.")
                            }
                        } else {
                            Log.e("Post 여부", "Post 성공하지만 응답 데이터가 비어있습니다.")
                        }
                    } else {
                        Log.e("Post 여부", "Post 실패: 응답 코드 = ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CreateProjectResponse>, t: Throwable) {
                    Log.e("Post 여부", "Post 실패: 네트워크 또는 기타 오류", t)
                }
            })
        }

        return binding.root
    }

    private fun createBitmapRequestBody(bitmap: Bitmap): RequestBody {
        return object : RequestBody() {
            override fun contentType(): MediaType = "image/jpeg".toMediaType()
            override fun writeTo(sink: BufferedSink) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
            }
        }
    }

    private fun updateButtonState() {
        val name = binding.pjName.text.toString()
        val start = binding.pjStart.text.toString()
        val end = binding.pjEnd.text.toString()
        val backgroundColor = binding.createCol.backgroundTintList?.defaultColor ?: 0
        val hexColor = String.format("#%06X", 0xFFFFFF and backgroundColor)
        val img = selectedImageUri?.toString() ?: ""

        // 이미지 선택 여부 확인
        val isImgSelected = selectedImageUri != null
        Log.e("이미지", "${isImgSelected}")

        // 로그 추가
        Log.e("조건 확인", "name: $name, start: $start, end: $end, hexColor: $hexColor, isImgSelected: $isImgSelected")
        Log.e("이미지 값","${img}")

        val enableButton = name.isNotEmpty() && start.isNotEmpty() && end.isNotEmpty() && hexColor.isNotEmpty() && isImgSelected

        // Add condition to check hexColor
        if (enableButton && hexColor != "#D9D9D9") {
            binding.btnCreateProject.isEnabled = true
            binding.btnCreateProject.setBackgroundResource(R.drawable.round_border3)
        } else {
            binding.btnCreateProject.isEnabled = false
            binding.btnCreateProject.setBackgroundResource(R.drawable.round_border3)
        }
    }

    override fun onColorSelected(col_num: Int) {
        val colorResId = when (col_num) {
            0 -> R.color.col_pink
            1 -> R.color.col_yellow
            2 -> R.color.col_orange
            3 -> R.color.col_green
            4 -> R.color.col_dpgreen
            5 -> R.color.col_blue
            6 -> R.color.col_dpblue
            7 -> R.color.col_purple
            8 -> R.color.col_black
            else -> R.color.col_grey
        }
        binding.createCol.backgroundTintList = ContextCompat.getColorStateList(requireContext(), colorResId)

        updateButtonState()
    }

    override fun onImgSelected(img_num: Int) {
        if (img_num == 1) {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.file_background)
            val imageBitmap = (drawable as BitmapDrawable).bitmap

            val fileName = "image${System.currentTimeMillis()}.jpg"
            val imageFile = File(requireContext().cacheDir, fileName)

            FileOutputStream(imageFile).use { outputStream ->
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }

            selectedImageUri = Uri.fromFile(imageFile)

            binding.imgAdd.setImageURI(selectedImageUri)
            binding.text.visibility = View.INVISIBLE

            updateButtonState()
        }
    }

    override fun onImgSelected(imageUri: Uri?) {
        if (imageUri != null) {
            binding.imgAdd.setImageURI(imageUri)
            binding.text.visibility = View.INVISIBLE
            selectedImageUri = imageUri
        }
        updateButtonState()
    }

    override fun onImgSelected(imageBitmap: Bitmap?) {
        if (imageBitmap != null) {
            val fileName = "image${System.currentTimeMillis()}.jpg"
            val imageFile = File(requireContext().cacheDir, fileName)

            FileOutputStream(imageFile).use { outputStream ->
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }

            selectedImageUri = Uri.fromFile(imageFile)

            binding.imgAdd.setImageURI(selectedImageUri)
            binding.text.visibility = View.INVISIBLE

            updateButtonState()
        }
    }
}