/*
package com.bws.musclefood.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bws.musclefood.R

class FeatchDataActivity : AppCompatActivity() {

    lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_featch_data)

       // database = AppDatabase.getDatabase(this)





      */
/*  database.contactDao().getContact().observe(this, Observer {


           GlobalScope.launch {
              val ids =  database.contactDao().getMobileNo(5).toString()
               Log.d("IDDDSSS==",ids.toString())
               println("ID=="+Gson().toJson(ids))

               val dt = database.contactDao().getMobileNo1()

              // val idBtween = database.contactDao().getIds(10,11).toString()

               Log.d("IDDDSSS==",dt.toString())
              println("ID=="+Gson().toJson(dt))


           }

        })*//*


       */
/* database.contactDao().getContactData().observe(this@FeatchDataActivity, Observer {

            val dt = it
            println("ID=="+dt[0].name)

        })*//*

    }
}*/
