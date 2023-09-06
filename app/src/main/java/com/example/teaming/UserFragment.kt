package com.example.teaming


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teaming.databinding.FragmentUserBinding
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class UserFragment : Fragment(), ImgDialog.OnImgSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: FragmentUserBinding
    private lateinit var backCallback: OnBackPressedCallback
    private var selectedImageUri: Uri? = null

    private var img_Selected = false

    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater,container,false)
        fragmentManager = requireActivity().supportFragmentManager

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val memberId = sharedPreference.getInt("memberId",-1)

        val callMyPage = RetrofitApi.getRetrofitService.myPage(memberId)

        callMyPage.enqueue(object : Callback<MyPageResponse> {
            override fun onResponse(call: Call<MyPageResponse>, response: Response<MyPageResponse>) {
                if (response.isSuccessful) {
                    val mypageResponse = response.body()
                    if (mypageResponse != null) {

                        username = mypageResponse.data.name
                        val email = mypageResponse.data.email

                        binding.name.text = username
                        binding.email.text = email
                        Log.e("이미지명","${mypageResponse.data.profileImage}")

                        Glide.with(requireContext())
                            .load(mypageResponse.data.profileImage)
                            .error(R.drawable.profile_default)
                            .fitCenter()
                            .into(binding.ButtonImg)

                        Log.d("R_LoginActivity", "${mypageResponse.data}")
                    }
                } else {
                    Log.d("R_LoginActivity", "API 호출 실패: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<MyPageResponse>, t: Throwable) {
                Log.e("R_LoginActivity", "로그인 API 호출 실패", t)
            }
        })


        binding.ButtonImg.setOnClickListener{
            val imgDialog = ImgDialog()
            imgDialog.show(requireActivity().supportFragmentManager,"ImgDialog")
            // target설정을 해야 interface사용이 가능하다고 함
            imgDialog.setTargetFragment(this,2)
        }

        binding.secret.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,ChangeNum())
                .addToBackStack(null)
                .commit()
        }

        binding.ButtonPencil.setOnClickListener {

            binding.byeFrame.visibility = View.GONE
            binding.frameLayout.visibility = View.VISIBLE

            val bundle = Bundle()

            bundle.putString("user_name",username)

            Log.d("보내기전","$bundle")

            val nameFragment = NameFragment()
            nameFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout,nameFragment)
                .commit()

        }

        binding.goLogout.setOnClickListener{
            showLogoutDialog()
        }

        backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.name.visibility = View.VISIBLE
                binding.ButtonPencil.visibility = View.VISIBLE

                fragmentManager.popBackStackImmediate()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backCallback.remove()
    }

    override fun onImgSelected(img_num: Int) {
        if (img_num == 1) {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.profile_default)
            val imageBitmap = (drawable as BitmapDrawable).bitmap

            val fileName = "image${System.currentTimeMillis()}.jpg"
            val imageFile = File(requireContext().cacheDir, fileName)

            FileOutputStream(imageFile).use { outputStream ->
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }
            selectedImageUri = Uri.fromFile(imageFile)

            binding.ButtonImg.setImageURI(selectedImageUri)
            binding.textImg.visibility = View.INVISIBLE
            img_Selected = true

            // 이미지가 선택되었을 때 여기서 Patch 작업을 수행하도록 호출
            performImagePatch()
        }
    }

    override fun onImgSelected(imageUri: Uri?) {
        if (imageUri != null) {
            binding.ButtonImg.setImageURI(imageUri)
            binding.textImg.visibility = View.INVISIBLE
            selectedImageUri = imageUri

            img_Selected = true

            // 이미지가 선택되었을 때 여기서 Patch 작업을 수행하도록 호출
            performImagePatch()
        }
    }

    override fun onImgSelected(imageBitmap: Bitmap?) {
        if (imageBitmap != null) {
            val fileName = "image${System.currentTimeMillis()}.jpg"
            val imageFile = File(requireContext().cacheDir, fileName)

            FileOutputStream(imageFile).use { outputStream ->
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }

            selectedImageUri = Uri.fromFile(imageFile)

            binding.ButtonImg.setImageURI(selectedImageUri)
            binding.textImg.visibility = View.INVISIBLE

            img_Selected = true

            // 이미지가 선택되었을 때 여기서 Patch 작업을 수행하도록 호출
            performImagePatch()
        }
    }

    // 이미지 변경 작업 수행 메서드
    private fun performImagePatch() {
        // 이미지 선택 후 이곳에서 Patch 작업을 수행하도록 처리
        // 이미지 변경 작업의 코드를 이곳에 적용하면 됨

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val memberId = sharedPreference.getInt("memberId",-1)

        var imagePart: MultipartBody.Part? = null

        if (img_Selected) {
            selectedImageUri?.let { uri ->
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val imageByteArray = inputStream?.readBytes()

                imageByteArray?.let {
                    val imageRequestBody = createBitmapRequestBody(BitmapFactory.decodeByteArray(it, 0, it.size))
                    imagePart = MultipartBody.Part.createFormData("change_image_file", uri.lastPathSegment, imageRequestBody)
                }
            }
        } else {
            // 이미지뷰에서 Drawable 가져오기
            val drawable = binding.ButtonImg.drawable
            // Drawable을 Bitmap으로 변환
            val imageBitmap = (drawable as BitmapDrawable).bitmap

            // Bitmap을 ByteArray로 변환
            val outputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            val imageByteArray = outputStream.toByteArray()

            // ByteArray를 RequestBody로 변환
            val imageRequestBody = RequestBody.create("image/jpeg".toMediaType(), imageByteArray)

            // MultipartBody.Part 생성
            imagePart = MultipartBody.Part.createFormData("change_image_file", "image.jpg", imageRequestBody)
        }

        val call = RetrofitApi.getRetrofitService.changeProfileImg(memberId, change_image_file = imagePart!!)

        call.enqueue(object : Callback<MemberChangeProfileImageResponse> {
            override fun onResponse(call: Call<MemberChangeProfileImageResponse>, response: Response<MemberChangeProfileImageResponse>) {
                if (response.isSuccessful) {
                    val changeProfileResponse = response.body()
                    if (changeProfileResponse != null) {
                        binding.textImg.visibility = View.INVISIBLE
                        val changeProfileProjects = changeProfileResponse.data

                        if(changeProfileProjects != null){
                            Log.e("이미지 Patch 여부", "이미지 Patch 성공")
                            Log.e("성공","${changeProfileResponse.data}, ${changeProfileResponse.status}, ${changeProfileResponse.message}")
                        }
                    }
                } else {
                    Log.e("이미지 Patch 여부", "이미지 Patch 실패: 응답 코드 = ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MemberChangeProfileImageResponse>, t: Throwable) {
                Log.e("이미지 Patch 여부", "이미지 Patch 실패: 네트워크 또는 기타 오류", t)
            }
        })
    }


    private fun createBitmapRequestBody(bitmap: Bitmap): RequestBody {
        return object : RequestBody() {
            override fun contentType(): MediaType = "image/jpeg".toMediaType()
            override fun writeTo(sink: BufferedSink) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
            }
        }
    }

    private fun showLogoutDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.activity_dialog_login_fir)

        val cancelBtn = dialog.findViewById<ImageButton>(R.id.logout_cancle)
        val logoutBtn = dialog.findViewById<ImageButton>(R.id.logout_btn)

        cancelBtn.setOnClickListener {
            dialog.dismiss() // 다이얼로그 종료
        }

        logoutBtn.setOnClickListener {
            val auto = requireActivity().getSharedPreferences("autoLogin", AppCompatActivity.MODE_PRIVATE)
            val autoLoginEdit = auto.edit()

            autoLoginEdit.putBoolean("autoLoginUse", false)
            autoLoginEdit.putString("Id", null)
            autoLoginEdit.putString("Pw", null)
            autoLoginEdit.commit()

            showLogoutConfirmationDialog()
            dialog.dismiss()

        }

        dialog.show()
    }
    private fun showLogoutConfirmationDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.activity_dialog_login_sec)

        val logoutOkBtn = dialog.findViewById<ImageButton>(R.id.logout_ok)

        logoutOkBtn.setOnClickListener {
            dialog.dismiss() // 다이얼로그 종료
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        dialog.show()
    }


}

