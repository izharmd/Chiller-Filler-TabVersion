package com.bws.musclefood.delivery.choosedeliveryaddress

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.AddNewAddressActivity
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionListResponse
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.myinterface.CallbackInterface
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.AddNewAddressViewModel
import com.bws.musclefood.viewmodels.DeliveryOptionViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_new_address.*


class ChooseDelAdapter(val activity: AppCompatActivity, val mList: ArrayList<ChooseDelModel>) :
    RecyclerView.Adapter<ChooseDelAdapter.ViewHolder>() {

    var context: Context? = null
    private var lastCheckedRB: RadioButton? = null

    lateinit var preferenceConnector: PreferenceConnector

    lateinit var addNewAddressViewModel: AddNewAddressViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_choose_adress_list, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        preferenceConnector = PreferenceConnector(context!!)
        val itemProduct = mList[position]
        holder.txtDefaultAdd.text = "(" + itemProduct.DeliveryAddressName + ")"
        holder.txtName.text = preferenceConnector.getValueString("FULL_NAME").toString()


        Constant.fullAddress = itemProduct.DeliveryAddressHouseNumber +
                " " + itemProduct.DeliveryAddressHouseNumber
        " " + itemProduct.DeliveryAddressLine1
        " " + itemProduct.DeliveryAddressLine2
        " " + itemProduct.DeliveryCity
        " " + itemProduct.DeliveryPostcode

        holder.txtFullAddress.text = Constant.fullAddress

        holder.txtPhone.text = itemProduct.DeliveryContactNumber

        val defaultAddress = itemProduct.DefaultAddressFlag

        val defaoutAdd = itemProduct.DefaultAddressFlag
        if (defaoutAdd == "Y") {
            holder.txtDefault.text = "Default Address"
            holder.txtDefault.visibility = View.VISIBLE
        } else {
            holder.txtDefault.visibility = View.GONE
        }

        holder.rdDefaultAddress.isChecked = defaultAddress == "Y"


        holder.itemView.setOnClickListener {

            holder.rdDefaultAddress.isChecked = true
            if (lastCheckedRB != null) {
                lastCheckedRB?.isChecked = false
            }
            lastCheckedRB = holder.rdDefaultAddress


            val body =
                RequestBodies.AddEditDeliveryDetails(
                    itemProduct.ID,
                    preferenceConnector.getValueString("USER_ID").toString(),
                    itemProduct.DeliveryAddressName,
                    itemProduct.DeliveryAddressHouseNumber,
                    itemProduct.DeliveryAddressLine1,
                    itemProduct.DeliveryAddressLine2,
                    itemProduct.DeliveryCity,
                    itemProduct.DeliveryPostcode,
                    itemProduct.DeliveryContactNumber,
                    "",
                    "",
                    "Y"
                )

            addNewAddress(body)

        }

        holder.txtEditAddress.setOnClickListener {

            Constant.addressType = "Edit"

            val intent = Intent(context, AddNewAddressActivity::class.java)

            intent.putExtra("ID", itemProduct.ID)
            intent.putExtra("UserID", itemProduct.UserID)
            intent.putExtra("DeliveryAddressName", itemProduct.DeliveryAddressName)
            intent.putExtra("DeliveryAddressHouseNumber", itemProduct.DeliveryAddressHouseNumber)
            intent.putExtra("DeliveryAddressLine1", itemProduct.DeliveryAddressLine1)
            intent.putExtra("DeliveryAddressLine2", itemProduct.DeliveryAddressLine2)
            intent.putExtra("DeliveryCity", itemProduct.DeliveryCity)
            intent.putExtra("DeliveryPostcode", itemProduct.DeliveryPostcode)
            intent.putExtra("DeliveryContactNumber", itemProduct.DeliveryContactNumber)
            intent.putExtra("DefaultAddressFlag", itemProduct.DefaultAddressFlag)

            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val rdDefaultAddress: RadioButton = itemView.findViewById(R.id.rdDefaultAddress)
        val txtDefaultAdd: TextView = itemView.findViewById(R.id.txtDefaultAdd)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtFullAddress: TextView = itemView.findViewById(R.id.txtFullAddress)
        val txtPhone: TextView = itemView.findViewById(R.id.txtPhone)
        val txtEditAddress: TextView = itemView.findViewById(R.id.txtEditAddress)
        val txtDefault: TextView = itemView.findViewById(R.id.txtDefault)

    }

    fun addNewAddress(body: RequestBodies.AddEditDeliveryDetails) {

        println("ADDRESS==="+Gson().toJson(body).toString())


        addNewAddressViewModel = ViewModelProvider(
            activity,
            FactoryProvider(Repository(), activity)
        ).get(AddNewAddressViewModel::class.java)

        addNewAddressViewModel.AddEditDeliveryDetails(body)
        val loadingDialog = LoadingDialog.progressDialog(activity)



        addNewAddressViewModel.response.observe(activity) {

            when (it) {

                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.dismiss()
                }
                is Resources.Success -> {

                    val status = it.data?.StatusCode

                    if (status == "200") {
                        AlertDialog().dialogPaymentDetailsAdd(
                            activity,
                            it.data?.StatusMSG.toString()
                        )
                    } else {
                        AlertDialog().dialogPaymentDetailsAdd(
                            activity,
                            it.data?.StatusMSG.toString()
                        )
                    }

                    loadingDialog.dismiss()

                }
                is Resources.Error -> {
                    loadingDialog.dismiss()
                }
            }
        }

    }
}
