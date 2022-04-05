package com.bws.musclefood.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


import android.Manifest
import android.app.Dialog

import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*

import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bws.musclefood.R
import com.bws.musclefood.urils.AlertDialog
import com.bws.musclefood.urils.PreferenceConnector

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.spTitle
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ChangeProfileActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2

    var jobTitle = 0

    lateinit var preferenceConnector: PreferenceConnector

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

        if (imageUrl !== null) {
            Glide.with(this)
                .load(imageUrl)
                .into(imvProfile)
        } else {
            imvProfile.setImageResource(R.drawable.ic_launcher_background)
        }

        when (title) {
            "Mr." -> {
                jobTitle = 0
            }
            "Mrs." -> {
                jobTitle =1
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
                    AlertDialog().dialog(this,"Please enter first name")
                }
                edtLastNameProfile.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter last name")
                }
                edtEmailProfile.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter email id")
                }
                edtPhoneNoProfile.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter phone no")
                }
                edtPhoneNoProfile.text.length < 10 -> {
                    AlertDialog().dialog(this,"Please enter valid phone no")
                }
                else -> {
                    AlertDialog().dialog(this,"Profile update successfully")
                }
            }
        }

        imvProfile.setOnClickListener {
            dialogTakePhoto()
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

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()

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
}