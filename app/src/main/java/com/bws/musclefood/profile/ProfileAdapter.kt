package com.bws.musclefood.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.choosedeliveryaddress.ChooseDeliveryAddressActivity
import com.bws.musclefood.orders.searchorder.SearchOrderActivity
import com.bws.musclefood.payment.PaymentActivity

class ProfileAdapter(val mList: List<ProfileModel>) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itmProfile = mList[position]
        holder.txtProfile.text = itmProfile.name



        holder.itemView.setOnClickListener() {
            if(position == 0){
                context?.startActivity(Intent(context,ChangeProfileActivity::class.java))
            }else if(position == 1){
                Constant.deliveryAddress = "Delivery Address"
                context?.startActivity(Intent(context,ChooseDeliveryAddressActivity::class.java))
            }else if(position == 2){
                context?.startActivity(Intent(context, SearchOrderActivity::class.java))
            }else if(position == 3){
                Constant.hidePaymentSection = "YES"
                Constant.paymentDetails = "Payment Details"
                context?.startActivity(Intent(context,PaymentActivity::class.java))
            }else if(position == 4){
                context?.startActivity(Intent(context,ContactPreferenceActivity::class.java))
            }else if(position == 5){

            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtProfile: TextView = itemView.findViewById(R.id.txtProfile)
    }

}