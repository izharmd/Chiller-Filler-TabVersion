package com.bws.musclefood.delivery.choosedeliveryaddress

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.AddNewAddressActivity
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionListResponse
import com.bws.musclefood.myinterface.CallbackInterface
import com.bws.musclefood.utils.PreferenceConnector


class ChooseDelAdapter(val mList: ArrayList<ChooseDelModel>) :
    RecyclerView.Adapter<ChooseDelAdapter.ViewHolder>() {

    var context: Context? = null
    private var lastCheckedRB: RadioButton? = null

    lateinit var preferenceConnector: PreferenceConnector


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
        holder.txtDefaultAdd.text = itemProduct.DeliveryAddressName
        holder.txtName.text = preferenceConnector.getValueString("FULL_NAME").toString()
        holder.txtFullAddress.text =
            itemProduct.DeliveryAddressHouseNumber +
                    " " + itemProduct.DeliveryAddressHouseNumber
                    " " + itemProduct.DeliveryAddressLine1
                    " " + itemProduct.DeliveryAddressLine2
                    " " + itemProduct.DeliveryCity
                    " " + itemProduct.DeliveryPostcode

         holder.txtPhone.text = itemProduct.DeliveryContactNumber

        val defaultAddress = itemProduct.DefaultAddressFlag

        holder.rdDefaultAddress.isChecked = defaultAddress == "Y"


        holder.itemView.setOnClickListener {

            holder.rdDefaultAddress.isChecked = true
            if (lastCheckedRB != null) {
                lastCheckedRB?.isChecked = false
            }
            lastCheckedRB = holder.rdDefaultAddress

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

    }
}
