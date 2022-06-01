package com.bws.musclefood.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


import android.Manifest
import android.app.Dialog

import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*

import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bws.musclefood.R
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.myinterface.CallbackInterface
import com.bws.musclefood.itemcategory.productlist.ProductListResponseItem
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.UpdateProfileViewModel
import com.bws.musclefood.viewmodels.UserProfileDetailsModel

import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.spTitle
import kotlinx.android.synthetic.main.tool_bar_address.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class ChangeProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,CallbackInterface {

    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2

    var jobTitle = 0
    var strTitle = ""

    lateinit var preferenceConnector: PreferenceConnector
    lateinit var updateProfileViewModel: UpdateProfileViewModel
    lateinit var userProfileDetailsViewModel: UserProfileDetailsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.hide()

        imvSaveaddress.visibility = View.GONE
        txtTxtHeader.text = "Profile"

        preferenceConnector = PreferenceConnector(this)
        edtFname.setText(preferenceConnector.getValueString("FIRST_NAME"))
        edtLastNameProfile.setText(preferenceConnector.getValueString("LAST_NAME"))
        edtEmailProfile.setText(preferenceConnector.getValueString("EMAIL_ID"))
        edtPhoneNoProfile.setText(preferenceConnector.getValueString("MOBILE_NO"))
        var title = preferenceConnector.getValueString("TITLE")

        val imageUrl = preferenceConnector.getValueString("PROFILE_URL")

       /* if (imageUrl !== null) {
            Glide.with(this)
                .load(imageUrl)
                .into(imvProfile)
        } else {
            imvProfile.setImageResource(R.drawable.user_account_circle_24)
        }*/


        when (title) {
            "Mr." -> {
                jobTitle = 0
            }
            "Mrs." -> {
                jobTitle = 1
            }
            "Miss." -> {
                jobTitle = 2
            }
            "Doctor." -> {
                jobTitle = 3
            }
        }

        txtUpdate.setOnClickListener {

            when {
                edtFname.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter first name")
                }
                edtLastNameProfile.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter last name")
                }
                edtEmailProfile.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter email id")
                }
                edtPhoneNoProfile.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter phone no")
                }
                edtPhoneNoProfile.text.length < 10 -> {
                    AlertDialog().dialog(this, "Please enter valid phone no")
                }
                else -> {
                    callUpdateProfileAPI()

                }
            }
        }

        imvProfile.setOnClickListener {
            dialogTakePhoto()
        }

        imvBack.setOnClickListener {
            finish()
        }

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.arrTitle,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTitle.adapter = adapter
        spTitle.setSelection(jobTitle)
        spTitle.onItemSelectedListener = this


        // FETCH USER DETAILS FROM SERVER
        callGetUserProfileDetails()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        strTitle = parent?.getItemAtPosition(position).toString()

    }


    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION
            )
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.extras?.get("data") as Bitmap

               // val bitmap = (image.getDrawable() as BitmapDrawable).getBitmap()
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                val image = stream.toByteArray()

                val jsonObj = JSONObject()

                jsonObj.put("UserID","2")

                val imageStrem = JSONObject()
                val _Identity = JSONObject()
                val _Identity2 = JSONObject()


                imageStrem.put("__identity",_Identity)

                _Identity.put("__identity",_Identity2)

                jsonObj.put("ImageByte",image)
                jsonObj.put("ImageStream",imageStrem)

                Log.d("IMAGE JSON==",jsonObj.toString())
                Log.d("IMAGE JSON==",jsonObj.toString())

                imvProfile.setImageBitmap(bitmap)

            } else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.getData()
                imvProfile.setImageURI(uri)
            }
        }
    }


    fun dialogTakePhoto() {
        val dialog = Dialog(this, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_open_camara)
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)
        val txtTakePhoto: TextView = dialog.findViewById(R.id.txtTakePhoto)
        val txtChooseFromGallary: TextView = dialog.findViewById(R.id.txtChooseFromGallary)

        txtTakePhoto.setOnClickListener {
            openCamera()
            dialog.dismiss()
        }

        txtChooseFromGallary.setOnClickListener {
            openGallery()
            dialog.dismiss()
        }


        imv_cross.setOnClickListener() {
            dialog.dismiss()
        }

        dialog.show()
    }


    fun callUpdateProfileAPI() {

        updateProfileViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(UpdateProfileViewModel::class.java)


        val body = RequestBodies.UpdateProfileBody(
            preferenceConnector.getValueString("USER_ID").toString(),
            strTitle,
            edtFname.text.toString(),
            edtLastNameProfile.text.toString(),
            preferenceConnector.getValueString("COMPANY_NAME").toString(),
            preferenceConnector.getValueString("VAT_NO").toString(),
            edtEmailProfile.text.toString(),
            edtPhoneNoProfile.text.toString()
        )

        updateProfileViewModel.getUpdateProfile(body)

        val loadingDialog = LoadingDialog.progressDialog(this)

        updateProfileViewModel.resultUpdateProfile.observe(this) {

            when (it) {

                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.dismiss()
                    AlertDialog().dialog(this, it.noInternetMessage.toString())
                    this.viewModelStore.clear()
                }
                is Resources.Success -> {

                    if (it.data?.StatusCode == "200") {
                         AlertDialog().dialog(this, "Profile update successfully")
                        //AlertDialog().dialog(this, it.data?.StatusMSG)
                    } else {
                        AlertDialog().dialog(this, it.data?.StatusMSG.toString())
                    }

                    loadingDialog.dismiss()
                    this.viewModelStore.clear()
                }
                is Resources.Error -> {
                    loadingDialog.dismiss()
                    AlertDialog().dialog(this, it.errorMessage.toString())
                    this.viewModelStore.clear()
                }
            }
        }

    }


    fun callGetUserProfileDetails() {
        userProfileDetailsViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(UserProfileDetailsModel::class.java)
        val body = RequestBodies.GetUserProfileDetailsBody(
            preferenceConnector.getValueString("USER_ID").toString()
        )
        userProfileDetailsViewModel.getUserProfileDetails(body)
        val loadingDialog = LoadingDialog.progressDialog(this)
        userProfileDetailsViewModel.resultUserProfileDetails.observe(this) {
            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.dismiss()
                    AlertDialog().dialog(this, it.noInternetMessage.toString())
                    this.viewModelStore.clear()
                }
                is Resources.Success -> {
                    if (it.data?.StatusCode == "200") {
                        edtFname.setText(it.data.FirstName)
                        edtLastNameProfile.setText(it.data.FirstName)
                        edtEmailProfile.setText(it.data.EmailID)
                        edtPhoneNoProfile.setText(it.data.MobilePhone)

                        var imageUrl = it.data.ProfileImage

                        println("PROFILE IMAGE=="+imageUrl)

                        if (imageUrl !== null) {
                            Glide.with(this)
                                .load(imageUrl)
                                .into(imvProfile)
                        } else {
                            imvProfile.setImageResource(R.drawable.user_account_circle_24)
                        }

                    } else {
                        AlertDialog().dialog(this, it.data?.StatusMSG.toString())
                    }

                    loadingDialog.dismiss()

                }
                is Resources.Error -> {
                    AlertDialog().dialog(this, it.errorMessage.toString())
                    loadingDialog.dismiss()
                }
            }
        }
    }

    override fun passResultCallback(message: ProductListResponseItem) {
      //  Toast.makeText(this,message.ProductName,Toast.LENGTH_SHORT).show()
    }
}