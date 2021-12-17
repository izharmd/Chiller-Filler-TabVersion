package com.bws.musclefood.delivery.choosedeliveryaddress

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R


class ChooseDelAdapter(val mList: List<ChooseDelModel>) :
    RecyclerView.Adapter<ChooseDelAdapter.ViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_choose_adress_list, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemProduct = mList[position]
        holder.txtDefaultAdd.text = itemProduct.defaultAdd
        holder.txtName.text = itemProduct.fullName
        holder.txtFullAddress.text = itemProduct.fullAddress
        holder.txtPhone.text = itemProduct.phoneNo
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtDefaultAdd: TextView = itemView.findViewById(R.id.txtDefaultAdd)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtFullAddress: TextView = itemView.findViewById(R.id.txtFullAddress)
        val txtPhone: TextView = itemView.findViewById(R.id.txtPhone)
    }
}
