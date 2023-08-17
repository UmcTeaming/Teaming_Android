package com.example.teaming

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import com.example.teaming.databinding.FragmentCreateBinding
import com.example.teaming.databinding.FragmentFileBinding
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

        binding.btnCreateProject.setOnClickListener {
            //val imageFile: File = // 이미지 파일을 얻는 방법에 따라 설정
            val backgroundColor = binding.createCol.backgroundTintList?.defaultColor ?: 0

            val createData = CreateProjectResponse(
                binding.pjName.text.toString(),
                selectedImageUri?.toString() ?: "", // 이미지 파일의 경로
                binding.pjStart.text.toString(),
                binding.pjEnd.text.toString(),
                String.format("#%06X", 0xFFFFFF and backgroundColor)
            )

            val bundle = Bundle().apply {
                putString("projectName", createData.project_name)
                putString("imageUri", createData.project_image)
                putString("startDate", createData.start_date)
                putString("endDate", createData.end_date)
                putString("projectColor", createData.project_color)
            }
            Log.d("생성프로젝트","${bundle}")

            val dialog = PjCompleteDialog()
            dialog.arguments = bundle
            dialog.show(requireActivity().supportFragmentManager,"PjCompleteDialog")
        }
        return binding.root
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
    }

    override fun onImgSelected(img_num: Int) {
        if(img_num==1) {
            binding.imgAdd.setImageResource(R.drawable.file_background)
            binding.text.visibility = View.INVISIBLE
            // null로 보내서 나중에 요청했을 때 image가 null이면 기본 이미지로 들어가도록 해야 함
            selectedImageUri = null
        }
    }

    override fun onImgSelected(imageUri: Uri?) {
        if (imageUri != null) {
            // 이미지를 imgAdd ImageView에 추가하고 처리합니다.
            binding.imgAdd.setImageURI(imageUri)
            binding.text.visibility = View.INVISIBLE
            selectedImageUri = imageUri
        }
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
    }
}