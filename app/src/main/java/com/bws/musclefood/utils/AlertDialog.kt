package com.bws.musclefood.utils

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant.Companion.totalCartItem
import com.bws.musclefood.itemcategory.productlist.ProductListActivity

class AlertDialog {

    fun dialog(activity: Activity,message:String){
        val dialog = Dialog(activity, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dailog_alert)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val txt_ok: TextView = dialog.findViewById(R.id.txt_ok)
        val txtMessage: TextView = dialog.findViewById(R.id.txtMessage)
        txtMessage.text = message

        txt_ok.setOnClickListener(){
            dialog.dismiss()
        }
        dialog.show()
    }

    fun dialogPaymentSuccessFull(activity: Activity,message:String){
        val dialog = Dialog(activity, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dailog_alert)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val txt_ok: TextView = dialog.findViewById(R.id.txt_ok)
        val txtMessage: TextView = dialog.findViewById(R.id.txtMessage)
        txtMessage.text = message

        txt_ok.setOnClickListener(){
            activity.startActivity(Intent(activity,ProductListActivity::class.java))
            val i = Intent(activity, ProductListActivity::class.java)        // Specify any activity here e.g. home or splash or login etc
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("EXIT", true)
            activity.startActivity(i)
            activity.finish()

            //SET
            totalCartItem = 0

            dialog.dismiss()
        }

        dialog.show()
    }


    fun dialogPaymentDetailsAdd(activity: Activity,message:String){
        val dialog = Dialog(activity, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dailog_alert)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val txt_ok: TextView = dialog.findViewById(R.id.txt_ok)
        val txtMessage: TextView = dialog.findViewById(R.id.txtMessage)
        txtMessage.text = message

        txt_ok.setOnClickListener(){
           /* activity.startActivity(Intent(activity,ProductListActivity::class.java))
            val i = Intent(activity, ProductListActivity::class.java)        // Specify any activity here e.g. home or splash or login etc
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("EXIT", true)
            activity.startActivity(i)
            activity.*/
            activity.finish()
            dialog.dismiss()
        }

        dialog.show()
    }
}