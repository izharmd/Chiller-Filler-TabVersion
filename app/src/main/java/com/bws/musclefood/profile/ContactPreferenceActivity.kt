package com.bws.musclefood.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bws.musclefood.R
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.UpdateProfileViewModel
import kotlinx.android.synthetic.main.activity_preference.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class ContactPreferenceActivity : AppCompatActivity() {
    lateinit var preferenceConnector: PreferenceConnector
    lateinit var updateProfileViewModel: UpdateProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        supportActionBar?.hide()
        txtTxtHeader.text = "Contact Preference"
        imvSaveaddress.visibility = View.GONE


        preferenceConnector = PreferenceConnector(this)
        edtFirstName.setText(preferenceConnector.getValueString("FIRST_NAME"))
        edtLastName.setText(preferenceConnector.getValueString("LAST_NAME"))
        edtEmail.setText(preferenceConnector.getValueString("EMAIL_ID"))
        edtPhoneNo.setText(preferenceConnector.getValueString("MOBILE_NO"))

        edtTitle.setText(preferenceConnector.getValueString("TITLE"))

        imvBack.setOnClickListener() {
            finish()
        }
        txtUpdatePrefrence.setOnClickListener() {
            when {
                edtFirstName.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter first name")
                }
                edtLastName.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter last name")
                }
                edtEmail.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter email id")
                }
                edtPhoneNo.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter phone no")
                }
                edtTitle.text.length < 10 -> {
                    AlertDialog().dialog(this, "Please enter valid phone no")
                }
                else -> {
                    callUpdateProfileAPIPrefrence()
                }

            }

        }
    }

    fun callUpdateProfileAPIPrefrence() {

        updateProfileViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(UpdateProfileViewModel::class.java)


        val body = RequestBodies.UpdateProfileBody(
            preferenceConnector.getValueString("USER_ID").toString(),

            edtTitle.text.toString(),
            edtFirstName.text.toString(),
            edtLastName.text.toString(),
            preferenceConnector.getValueString("COMPANY_NAME").toString(),
            preferenceConnector.getValueString("VAT_NO").toString(),
            edtEmail.text.toString(),
            edtPhoneNo.text.toString()
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
                        // AlertDialog().dialog(this, "Profile update successfully")
                        AlertDialog().dialog(this, it.data?.StatusMSG)
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
}