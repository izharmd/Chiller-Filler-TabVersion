package com.bws.musclefood.orders.fragment.fragmentCurrent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.SearchOrderViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.fragment_order.view.*

class CurrentOrderFragment : Fragment() {

    lateinit var searchOrderViewModel: SearchOrderViewModel
    lateinit var preferenceConnector: PreferenceConnector
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_order, container, false)

        view.recyCurrentOrder.layoutManager = LinearLayoutManager(context)
        preferenceConnector = PreferenceConnector(requireContext())

        searchOrderAPI()
        return view
    }


    fun searchOrderAPI() {
        searchOrderViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), requireContext())
        ).get(SearchOrderViewModel::class.java)

        val body = RequestBodies.SearchOrdersBody(
            preferenceConnector.getValueString("USER_ID").toString(),
            "1",
            "",
            Constant.fromDate,
            Constant.toDate,
            "CURRENT"
        )


        searchOrderViewModel.getSearchOrder(body)

        val loadingDialog = LoadingDialog.progressDialog(requireContext())

        searchOrderViewModel.resultSearchOrder.observe(viewLifecycleOwner) {
            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.dismiss()
                    Toast.makeText(requireActivity(), it.noInternetMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                is Resources.Success -> {
                    loadingDialog.dismiss()

                    if(it.data?.size != 0){
                        val adapter = CurrentOrderAdapter(it.data!!)
                        view?.recyCurrentOrder!!.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }else{
                        view?.txtNoOrder?.visibility = View.GONE
                    }


                }
                is Resources.Error -> {
                    loadingDialog.dismiss()
                    AlertDialog().dialog(requireActivity(), it.errorMessage.toString())
                }
            }
        }
    }
}


