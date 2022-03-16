package com.bws.musclefood.profile

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.a_profile_new.*

import kotlinx.android.synthetic.main.tool_bar_address.*

class MyProfileActivity:AppCompatActivity() {

    var arrProfile = ArrayList<ProfileModel>()

    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_profile_new)
        supportActionBar?.hide()
        txtTxtHeader.text = "Profile"
        imvSaveaddress.visibility = View.GONE

        recyProfile.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyProfile.addItemDecoration(DividerItemDecoration(dividerDrawable))

        arrProfile.add(ProfileModel("Name"))
        arrProfile.add(ProfileModel("Delivery Address"))
        arrProfile.add(ProfileModel("Orders"))
        arrProfile.add(ProfileModel("Payment Details"))
        arrProfile.add(ProfileModel("Contact Preference"))
        arrProfile.add(ProfileModel("Help"))


        val adapter = ProfileAdapter(arrProfile)
        recyProfile.adapter = adapter
        adapter.notifyDataSetChanged()



        imvProfile.setOnClickListener {
            dialogTakePhoto()
        }


        imvBack.setOnClickListener(){
            finish()
        }
    }


    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION)
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
                imvProfile.setImageBitmap(bitmap)
            }
            else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.getData()
                imvProfile.setImageURI(uri)
            }
        }
    }


    fun dialogTakePhoto() {
        val dialog = Dialog(this,R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_open_camara)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

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


        imv_cross.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }
}