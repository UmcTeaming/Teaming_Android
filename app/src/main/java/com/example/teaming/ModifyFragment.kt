package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teaming.databinding.FragmentModifyBinding
import com.example.teaming.databinding.InviteNoInfoDialogBinding
import com.example.teaming.databinding.InviteYesInfoDialogBinding
import com.example.teaming.databinding.PjInviteDialogBinding
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ModifyFragment : Fragment(),ColSelDialog.OnColorSelectedListener, ImgDialog.OnImgSelectedListener {
    private lateinit var binding: FragmentModifyBinding
    private val itemList = ArrayList<MemberData>()

    private var img_Selected = false

    private lateinit var pjInviteDialog: Dialog
    private lateinit var inviteYesInfoDialog: Dialog
    private lateinit var inviteNoInfoDialog: Dialog
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModifyBinding.inflate(inflater,container,false)

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val memberId = sharedPreference.getInt("memberId", -1)

        val projectId = arguments?.getInt("projectID")


        Log.e("modify memberId","${memberId}")
        Log.e("modify projectId","${projectId}")

        val infoModifyPage = RetrofitApi.getRetrofitService.getInfoModify(memberId,projectId)

        if(projectId!=null){
            infoModifyPage.enqueue(object : Callback<InfoProjectResponse> {
                override fun onResponse(
                    call: Call<InfoProjectResponse>,
                    response: Response<InfoProjectResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e("modify memberId","${memberId}")
                        Log.e("modify projectId","${projectId}")
                        val infoProjectResponse = response.body()
                        if (infoProjectResponse != null) {
                            val getinfoProjects = infoProjectResponse.data

                            if(getinfoProjects != null){
                                binding.modiName.setText(getinfoProjects.name)
                                binding.modiStart.setText(getinfoProjects.startDate)
                                binding.modiEnd.setText(getinfoProjects.endDate)
                                binding.text.visibility = View.INVISIBLE

                                Glide.with(requireContext())
                                    .load(getinfoProjects.image)
                                    .error(R.drawable.pj_image_default)
                                    .fitCenter()
                                    .into(binding.imgModi)

                                val hexColor = getinfoProjects.projectColor// 원하는 hex 색상 코드로 변경하세요
                                val color = Color.parseColor(hexColor)

                                Log.e("color","${getinfoProjects.projectColor}")

                                val colorStateList = ColorStateList.valueOf(color)
                                binding.modiCol.backgroundTintList = colorStateList

                                itemList.clear()
                                for (member in getinfoProjects.memberListDtos) {
                                    itemList.add(
                                        MemberData(
                                            member.member_image,
                                            member.member_name
                                        )
                                    )
                                }

                                val memberboard = binding.root.findViewById<RecyclerView>(R.id.member_modify)

                                val memberAdapter2 = MemberAdapter2(itemList)
                                memberAdapter2.notifyDataSetChanged()

                                memberboard.adapter = memberAdapter2
                                memberboard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                            }
                        }
                    } else {
                        Log.d("Fragment", "API 반호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<InfoProjectResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }


        // 팀 프로젝트 수정하기 버튼 누른 경우 PATCH 여기서 하기
        binding.btnModiProject.setOnClickListener {
            val projectName = binding.modiName.text.toString()
            val startDate = binding.modiStart.text.toString()
            val endDate = binding.modiEnd.text.toString()
            val backgroundColor = binding.modiCol.backgroundTintList?.defaultColor ?: 0
            val hexColor = String.format("#%06X", 0xFFFFFF and backgroundColor)

            var imagePart: MultipartBody.Part? = null

            if (img_Selected) {
                selectedImageUri?.let { uri ->
                    val inputStream = requireContext().contentResolver.openInputStream(uri)
                    val imageByteArray = inputStream?.readBytes()

                    imageByteArray?.let {
                        val imageRequestBody = createBitmapRequestBody(BitmapFactory.decodeByteArray(it, 0, it.size))
                        imagePart = MultipartBody.Part.createFormData("project_image", uri.lastPathSegment, imageRequestBody)
                    }
                }
            } else {
                // 이미지뷰에서 Drawable 가져오기
                val drawable = binding.imgModi.drawable
                // Drawable을 Bitmap으로 변환
                val imageBitmap = (drawable as BitmapDrawable).bitmap

                // Bitmap을 ByteArray로 변환
                val outputStream = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                val imageByteArray = outputStream.toByteArray()

                // ByteArray를 RequestBody로 변환
                val imageRequestBody = RequestBody.create("image/jpeg".toMediaType(), imageByteArray)

                // MultipartBody.Part 생성
                imagePart = MultipartBody.Part.createFormData("project_image", "image.jpg", imageRequestBody)
            }

            val nameRequestBody: RequestBody = RequestBody.create("text/plain".toMediaType(), projectName)
            val startRequestBody: RequestBody = RequestBody.create("text/plain".toMediaType(), startDate)
            val endRequestBody: RequestBody = RequestBody.create("text/plain".toMediaType(), endDate)
            val hexColorRequestBody: RequestBody = RequestBody.create("text/plain".toMediaType(), hexColor)

            val textHashMap = hashMapOf<String, RequestBody>()
            textHashMap["project_name"] = nameRequestBody
            textHashMap["start_date"] = startRequestBody
            textHashMap["end_date"] = endRequestBody
            textHashMap["project_color"] = hexColorRequestBody

            if (projectId != null){
                val modify = RetrofitApi.getRetrofitService.modifyProject(
                    memberId,
                    projectId,
                    projectImage = imagePart!!,
                    textHashMap
                )

                modify.enqueue(object : Callback<ModifyProjectResponse> {
                    override fun onResponse(call: Call<ModifyProjectResponse>, response: Response<ModifyProjectResponse>) {
                        if (response.isSuccessful) {
                            val modifyProjectResponse = response.body()
                            if (modifyProjectResponse != null) {
                                val projectId = modifyProjectResponse.data.project_id
                                if (projectId != null) {
                                    Log.e("Post 여부", "Post 성공: 프로젝트 ID = $projectId")

                                    val bundle = Bundle()
                                    bundle.putInt("modiProjectID",projectId!!)
                                    bundle.putInt("num",2)

                                    val dialog = PjCompleteDialog()
                                    dialog.arguments = bundle

                                    // Show the success dialog or perform other actions

                                    dialog.show(requireActivity().supportFragmentManager, "PjCompleteDialog")
                                } else {
                                    Log.e("Patch 여부", "Patch 성공하지만 프로젝트 ID가 없습니다.")
                                }
                            } else {
                                Log.e("Patch 여부", "Patch 성공하지만 응답 데이터가 비어있습니다.")
                            }
                        } else {
                            Log.e("Patch 여부", "Patch 실패: 응답 코드 = ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<ModifyProjectResponse>, t: Throwable) {
                        Log.e("Patch 여부", "Patch 실패: 네트워크 또는 기타 오류", t)
                    }
                })
            }
        }

        binding.btnModiUser.setOnClickListener{
            showPjInviteDialog()
        }

        binding.imgModi.setOnClickListener{
            val imgDialog = ImgDialog()
            imgDialog.show(requireActivity().supportFragmentManager,"ImgDialog")

            // target설정을 해야 interface사용이 가능하다고 함
            imgDialog.setTargetFragment(this,1)
        }

        binding.btnModiColor.setOnClickListener {
            val colSelDialog = ColSelDialog()
            colSelDialog.show(requireActivity().supportFragmentManager,"ColSelDialog")
            // target설정을 해야 interface사용이 가능하다고 함
            colSelDialog.setTargetFragment(this,0)
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
        binding.modiCol.backgroundTintList = ContextCompat.getColorStateList(requireContext(), colorResId)
    }

    private fun createBitmapRequestBody(bitmap: Bitmap): RequestBody {
        return object : RequestBody() {
            override fun contentType(): MediaType = "image/jpeg".toMediaType()
            override fun writeTo(sink: BufferedSink) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
            }
        }
    }

    private fun showPjInviteDialog() {
        pjInviteDialog = Dialog(requireContext())
        val dialogBinding: PjInviteDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.pj_invite_dialog,
            null,
            false
        )
        pjInviteDialog.setContentView(dialogBinding.root)

        dialogBinding.cancelChkButton.setOnClickListener {
            pjInviteDialog.dismiss()
        }

        dialogBinding.inviteChkBtn.setOnClickListener {

            val sharedPreference_mem = requireActivity().getSharedPreferences("memberId",
                Context.MODE_PRIVATE
            )
            val sharedPreference = requireActivity().getSharedPreferences("projectID_page",
                Context.MODE_PRIVATE
            )

            val memberId = sharedPreference_mem.getInt("memberId",-1)

            val projectId = sharedPreference.getInt("projectID_page",-1)

            val requestBodyData = InvitationsRequest(dialogBinding.emailWrite.text.toString())
            Log.d("리퀘스트","${requestBodyData}")
            val json = Gson().toJson(requestBodyData)
            val requestBody = RequestBody.create("application/json".toMediaType(), json)

            val callInvitation = RetrofitApi.getRetrofitService.invitation(memberId,projectId,requestBody)

            callInvitation.enqueue(object : Callback<InvitationsResponse> {
                override fun onResponse(call: Call<InvitationsResponse>, response: Response<InvitationsResponse>) {
                    if (response.isSuccessful) {
                        val invitationsResponse = response.body()
                        if (invitationsResponse != null) {
                            Log.d("Invitation", "API 호출 성공: ${invitationsResponse}")
                            pjInviteDialog.dismiss()
                            showInviteYesInfoDialog()

                            itemList.clear()
                            for (member in invitationsResponse.data.members) {
                                itemList.add(
                                    MemberData(
                                        member.memberImage,
                                        member.memberName
                                    )
                                )
                            }
                            while (itemList.size < 4) {
                                itemList.add(MemberData("no_profile", "기본"))
                            }

                            val memberboard = binding.root.findViewById<RecyclerView>(R.id.member)

                            val memberAdapter = MemberAdapter(itemList)
                            memberAdapter.notifyDataSetChanged()

                            memberboard.adapter = memberAdapter
                            memberboard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                            if(invitationsResponse.status != 200){
                                pjInviteDialog.dismiss()
                                showInviteNoInfoDialog(invitationsResponse.status)
                            }
                        }
                    } else {
                        Log.d("Invitation", "API 호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<InvitationsResponse>, t: Throwable) {
                    Log.e("Invitation", "로그인 API 호출 실패", t)
                }
            })


        }

        pjInviteDialog.show()
    }

    private fun showInviteYesInfoDialog() {
        inviteYesInfoDialog = Dialog(requireContext())
        val dialogBinding: InviteYesInfoDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.invite_yes_info_dialog,
            null,
            false
        )
        inviteYesInfoDialog.setContentView(dialogBinding.root)

        dialogBinding.closeYesBtn.setOnClickListener {
            inviteYesInfoDialog.dismiss()
        }

        inviteYesInfoDialog.show()
    }

    private fun showInviteNoInfoDialog(errorcode : Int) {
        inviteNoInfoDialog = Dialog(requireContext())
        val dialogBinding: InviteNoInfoDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.invite_no_info_dialog,
            null,
            false
        )

        if (errorcode == 208){
            dialogBinding.noWayHome.text = "이미 참여 중인 초대자입니다."
        }else if(errorcode == 404){
            dialogBinding.noWayHome.text = "회원이 아닌 초대자 입니다."
        }
        inviteNoInfoDialog.setContentView(dialogBinding.root)

        dialogBinding.yesBtn.setOnClickListener {
            inviteNoInfoDialog.dismiss()
        }

        dialogBinding.reWriteBtn.setOnClickListener {
            inviteNoInfoDialog.dismiss()
            showPjInviteDialog()
        }

        inviteNoInfoDialog.show()
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

            binding.imgModi.setImageURI(selectedImageUri)
            binding.text.visibility = View.INVISIBLE

            img_Selected = true
        }
    }

    override fun onImgSelected(imageUri: Uri?) {
        if (imageUri != null) {
            binding.imgModi.setImageURI(imageUri)
            binding.text.visibility = View.INVISIBLE
            selectedImageUri = imageUri
            img_Selected = true
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

            binding.imgModi.setImageURI(selectedImageUri)
            binding.text.visibility = View.INVISIBLE

            img_Selected = true

        }
    }
}