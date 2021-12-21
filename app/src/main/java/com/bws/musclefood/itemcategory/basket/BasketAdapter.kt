package com.bws.musclefood.itemcategory.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.orders.fragment.fragmentCurrent.CurrentOrderAdapter

class BasketAdapter(val mList:ArrayList<BasketModel>):RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    private var context:Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basket_list,parent,false)
        context = parent.context
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val basketModel = mList[position]
        holder.txtDeleteDate.text = basketModel.deleteDate
        holder.txtNo.text = basketModel.number
        holder.txtClientName.text = basketModel.clientName
        holder.txtValue.text = basketModel.value
        holder.txtValue.text = basketModel.status

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView) {
        
        val txtDeleteDate:TextView = itemView.findViewById(R.id.txtDeleteDate);
        val txtNo:TextView = itemView.findViewById(R.id.txtNo);
        val txtClientName:TextView = itemView.findViewById(R.id.txtClientName);
        val txtValue:TextView = itemView.findViewById(R.id.txtValue);
        val txtStatus:TextView = itemView.findViewById(R.id.txtStatus);

    }
}