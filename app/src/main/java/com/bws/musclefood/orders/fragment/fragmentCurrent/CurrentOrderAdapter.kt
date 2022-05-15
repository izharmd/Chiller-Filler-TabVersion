package com.bws.musclefood.orders.fragment.fragmentCurrent

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.orderItem
import com.bws.musclefood.orders.reorder.ReorderActivity
import com.bws.musclefood.orders.searchorder.OrderItemList
import com.bws.musclefood.orders.searchorder.SearchOrderResponseItem
import com.bws.musclefood.orders.vieworderdetails.ViewOrderDetailsActivity

class CurrentOrderAdapter(val mList:ArrayList<SearchOrderResponseItem>) :RecyclerView.Adapter<CurrentOrderAdapter.ViewHolder>(){
     private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_current_order,parent,false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentOrder = mList[position]

        holder.txtOrderDate.text = currentOrder.OrderDate
        holder.txtOrderNumber.text = currentOrder.OrderNumber
        holder.txtOrderValue.text = currentOrder.OrderAmount
        holder.txtOrderStatus.text = currentOrder.OrderStatus


        holder.txtViewOrder.setOnClickListener{

           orderItem = currentOrder.OrderItemList
            context?.startActivity(Intent(context,ViewOrderDetailsActivity::class.java))
        }

        holder.txtReOrder.setOnClickListener{
            Constant.orderNo = currentOrder.OrderNumber
            context?.startActivity(Intent(context,ReorderActivity::class.java))
        }

    }

    override fun getItemCount(): Int {

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