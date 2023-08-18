package com.example.teaming

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.teaming.databinding.FragmentCreateBinding
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class CreateFragment : Fragment(), ColSelDialog.OnColorSelectedListener, ImgDialog.OnImgSelectedListener {
    private lateinit var binding: FragmentCreateBinding
    private var selectedImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
            val start = binding.pjStart.text.toString()
            val end = binding.pjEnd.text.toString()
            val backgroundColor = binding.createCol.backgroundTintList?.defaultColor ?: 0
            val hexColor = String.format("#%06X", 0xFFFFFF and backgroundColor)
            //val img = selectedImageUri?.toString() ?: ""

            val requestData = CreateProjectRequest(name, "", start, end, hexColor) // Placeholder for image

            val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setPrettyPrinting()
                .create()

            val json = gson.toJson(requestData)
            val jsonRequestBody = RequestBody.create("application/json".toMediaType(), json)

            val imagePart: MultipartBody.Part? = selectedImageUri?.let {
                val file = File(it.path)
                val requestFile = RequestBody.create("image/*".toMediaType(), file)
                MultipartBody.Part.createFormData("projectImage", file.name, requestFile)
            }

            val call = RetrofitApi.getRetrofitService.createProject(memberId, jsonRequestBody, imagePart)

            call.enqueue(object : Callback<CreateProjectResponse> {
                override fun onResponse(
                    call: Call<CreateProjectResponse>,
                    response: Response<CreateProjectResponse>
                ) {
                    if (response.isSuccessful) {
                        val createProjectResponse = response.body()
                        if (createProjectResponse != null) {
                            val projectId = createProjectResponse.data?.project_id
                            if (projectId != null) {
                                Log.e("Post 여부", "Post 성공: 프로젝트 ID = $projectId")

                                // Show the success dialog or perform other actions
                                val dialog = PjCompleteDialog()
                                dialog.show(requireActivity().supportFragmentManager, "PjCompleteDialog")
                            } else {
                                Log.e("Post 여부", "Post 성공하지만 프로젝트 ID가 없습니다.")
                            }
                        } else {
                            Log.e("Post 여부", "Post 성공하지만 응답 데이터가 비어있습니다.")
                        }
                    } else {
                        Log.e("Post 여부", "Post 실패: 응답 코드 = ${response.code()}, 에러 메시지 = ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<CreateProjectResponse>, t: Throwable) {
                    Log.e("Post 여부", "Post 실패: 네트워크 또는 기타 오류", t)
                }
            })
            // 다이얼로그 표시
            val dialog = PjCompleteDialog()
            dialog.show(requireActivity().supportFragmentManager, "PjCompleteDialog")
        }

        return binding.root
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
            // Create an imageBitmap from the drawable resource
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.file_background)
            val imageBitmap = (drawable as BitmapDrawable).bitmap

            // Save the imageBitmap to a temporary file
            val imageFile = File.createTempFile("image", ".jpg", requireContext().cacheDir)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, FileOutputStream(imageFile))

            // Set the selectedImageUri to the Uri of the saved image file
            selectedImageUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                imageFile
            )

            // Update the UI
            binding.imgAdd.setImageBitmap(imageBitmap)
            binding.text.visibility = View.INVISIBLE
        }
        updateButtonState()
    }

    override fun onImgSelected(imageUri: Uri?) {
        if (imageUri != null) {
            // 이미지를 imgAdd ImageView에 추가하고 처리합니다.
            binding.imgAdd.setImageURI(imageUri)
            binding.text.visibility = View.INVISIBLE
            selectedImageUri = imageUri
        }
        updateButtonState()
    }

    override fun onImgSelected(imageBitmap: Bitmap?) {
        if (imageBitmap != null) {
            // Bitmap을 File로 저장하고 Uri 얻기
            val imageFile = File.createTempFile("image", ".jpg", requireContext().cacheDir)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, FileOutputStream(imageFile))
            selectedImageUri = Uri.fromFile(imageFile)

            binding.imgAdd.setImageBitmap(imageBitmap)
            binding.text.visibility = View.INVISIBLE
        }
        updateButtonState()
    }

    private fun getUriFromDrawableRes(context: Context, drawableResId: Int): Uri? {
        val drawable = ContextCompat.getDrawable(context, drawableResId) ?: return null
        val bitmap = (drawable as BitmapDrawable).bitmap

        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()

        // Create a unique file name
        val fileName = "image_${System.currentTimeMillis()}.png"

        val imageFile = File(cachePath, fileName)
        val stream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()

        return FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
    }
}