package com.bws.musclefood.orders.fragment.fragmentCurrent

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.itemcategory.ItemCategoryAdapter
import com.bws.musclefood.orders.reorder.ReorderActivity
import com.bws.musclefood.orders.vieworderdetails.ViewOrderDetailsActivity

class CurrentOrderAdapter(val mList:ArrayList<CurrentOrderModel>) :RecyclerView.Adapter<CurrentOrderAdapter.ViewHolder>(){
     private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_current_order,parent,false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentOrder = mList[position]

        holder.txtOrderDate.text = currentOrder.orderDate
        holder.txtOrderNumber.text = currentOrder.orderNumber
        holder.txtOrderValue.text = currentOrder.orderValue
        holder.txtOrderStatus.text = currentOrder.status

        val content = SpannableString(currentOrder.deliveryTrack)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        holder.txtTrack.text = content

       // val content2 = SpannableString(currentOrder.orderNumber)
        //content2.setSpan(UnderlineSpan(), 0, content.length, 0)
        //holder.txtOrderNumber.text = content2


        holder.txtViewOrder.setOnClickListener{
            context?.startActivity(Intent(context,ViewOrderDetailsActivity::class.java))
        }

        holder.txtReOrder.setOnClickListener{
            Constant.orderNo = currentOrder.orderNumber
            context?.startActivity(Intent(context,ReorderActivity::class.java))
        }

    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return mList.size
    }

    class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView){

        val txtOrderDate: TextView = itemView.findViewById(R.id.txtOrderDate)
        val txtOrderNumber: TextView = itemView.findViewById(R.id.txtOrderNumber)
        val txtOrderValue: TextView = itemView.findViewById(R.id.txtOrderValue)
        val txtOrderStatus: TextView = itemView.findViewById(R.id.txtOrderStatus)
        val txtTrack: TextView = itemView.findViewById(R.id.txtTrack)
        val txtReOrder: TextView = itemView.findViewById(R.id.txtReOrder)
        val txtViewOrder: TextView = itemView.findViewById(R.id.txtViewOrder)

    }
}