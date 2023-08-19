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
import com.example.teaming.databinding.FragmentCreateBinding
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
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

            // Convert name to JSON
            val nameJson = Gson().toJson(name)
            val nameRequestBody = RequestBody.create("text/plain".toMediaType(), nameJson)

            val startJson = Gson().toJson(start)
            val startRequestBody = RequestBody.create("text/plain".toMediaType(), startJson)

            val endJson = Gson().toJson(end)
            val endRequestBody = RequestBody.create("text/plain".toMediaType(), endJson)

            val hexColorJson = Gson().toJson(hexColor)
            val hexColorRequestBody = RequestBody.create("text/plain".toMediaType(), hexColorJson)

            val imageFileName = selectedImageUri?.lastPathSegment ?: "image.jpg"
            val imageFile = File(requireContext().cacheDir, imageFileName)

            val imageRequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
            val imagePart = MultipartBody.Part.createFormData("project_image", imageFile.name, imageRequestBody)

            // Print the data for debugging purposes
            Log.e("멀티파트", "imagePart: ${imageFile.name}, ${imageRequestBody.contentType()}, ${imageRequestBody.contentLength()} bytes")
            Log.e("멀티파트", "nameRequestBody: ${nameRequestBody.contentType()}, ${nameRequestBody.contentLength()} bytes, ${nameRequestBody.toString()}")
            Log.e("멀티파트", "startRequestBody: ${startRequestBody.contentType()}, ${startRequestBody.contentLength()} bytes, ${startRequestBody.toString()}")
            Log.e("멀티파트", "endRequestBody: ${endRequestBody.contentType()}, ${endRequestBody.contentLength()} bytes, ${endRequestBody.toString()}")
            Log.e("멀티파트", "hexColorRequestBody: ${hexColorRequestBody.contentType()}, ${hexColorRequestBody.contentLength()} bytes, ${hexColorRequestBody.toString()}")


            val memberId = sharedPreference.getInt("memberId", -1)

            val call = RetrofitApi.getRetrofitService.createProject(
                memberId,
                nameRequestBody,
                imagePart,
                startRequestBody,
                endRequestBody,
                hexColorRequestBody
            )

            call.enqueue(object : Callback<CreateProjectResponse> {
                override fun onResponse(call: Call<CreateProjectResponse>, response: Response<CreateProjectResponse>) {
                    if (response.isSuccessful) {
                        val createProjectResponse = response.body()
                        if (createProjectResponse != null) {
                            val projectId = createProjectResponse.data.project_id
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

    /*override fun onImgSelected(img_num: Int) {
        if (img_num == 1) {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.file_background)
            val imageBitmap = (drawable as BitmapDrawable).bitmap

            val imageFile = File(requireContext().cacheDir, "image${System.currentTimeMillis()}.jpg")
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, FileOutputStream(imageFile))

            selectedImageUri = Uri.fromFile(imageFile)

            binding.imgAdd.setImageURI(selectedImageUri)
            binding.text.visibility = View.INVISIBLE

            updateButtonState()
        }
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
            val imageFile = File(requireContext().cacheDir, "image${System.currentTimeMillis()}.jpg")
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, FileOutputStream(imageFile))

            selectedImageUri = Uri.fromFile(imageFile)

            binding.imgAdd.setImageURI(selectedImageUri)
            binding.text.visibility = View.INVISIBLE

            updateButtonState()
        }
    }*/

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