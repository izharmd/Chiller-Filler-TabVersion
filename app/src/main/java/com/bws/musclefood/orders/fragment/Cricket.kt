package com.bws.musclefood.orders.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.orders.fragment.fragmentCurrent.CurrentOrderAdapter
import com.bws.musclefood.orders.fragment.fragmentCurrent.CurrentOrderModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_football.view.*

class Cricket : Fragment() {
   override fun onCreateView(
   inflater: LayoutInflater, container: ViewGroup?,
   savedInstanceState: Bundle?
   ): View? {
      // Inflate the layout for this fragment
      val view: View = inflater!!.inflate(R.layout.fragment_football, container, false)

      view.recyCurrentOrder.layoutManager = LinearLayoutManager(context)
      val data = ArrayList<CurrentOrderModel>()

      view.txtNoOrder.visibility = View.GONE


      data.add(CurrentOrderModel("18/01/2021","27706830","£358.96","Shipped","Track"))
      data.add(CurrentOrderModel("05/09/2019","26262638","£379.96","Shipped","Track"))
     // data.add(CurrentOrderModel("17/12/2021","1761754","£50.20","Shipped","Track"))
     // data.add(CurrentOrderModel("18/11/2021","17654984","£70.00","Shipped","Track"))
     // data.add(CurrentOrderModel("20/11/2021","11054044","£20.80","Shipped","Track"))

      val adapter = CurrentOrderAdapter(data)
      view.recyCurrentOrder.adapter = adapter
      adapter.notifyDataSetChanged()


      return view
   }
}