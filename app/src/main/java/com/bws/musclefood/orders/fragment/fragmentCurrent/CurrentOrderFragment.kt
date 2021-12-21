package com.bws.musclefood.orders.fragment.fragmentCurrent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.ItemCategoryAdapter
import com.bws.musclefood.itemcategory.ItemCategoryModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_item_categoty.*
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.fragment_football.*
import kotlinx.android.synthetic.main.fragment_football.view.*
import java.util.zip.Inflater

class CurrentOrderFragment : Fragment() {
   override fun onCreateView(
   inflater: LayoutInflater, container: ViewGroup?,
   savedInstanceState: Bundle?
   ): View? {

      val view: View = inflater!!.inflate(R.layout.fragment_football, container, false)

      /* view.recyCurrentOrder.layoutManager = LinearLayoutManager(context)
       val data = ArrayList<CurrentOrderModel>()


       data.add(CurrentOrderModel("20/12/2021","11004344","£100.00","Not Shipped","Track"))
       data.add(CurrentOrderModel("25/12/2021","15610044","£150.60","Not Shipped","Track"))
       data.add(CurrentOrderModel("26/12/2021","17610044","£50.20","Order Confirm","Track"))
       data.add(CurrentOrderModel("01/01/2022","11004984","£70.00","Not Shipped","Track"))
       data.add(CurrentOrderModel("10/01/2022","11054044","£20.80","Out for Delivery","Track"))

       val adapter = CurrentOrderAdapter(data)
       view.recyCurrentOrder.adapter = adapter
       adapter.notifyDataSetChanged()
*/

      return view
   }
}


