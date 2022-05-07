package com.bws.musclefood.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.room.Room
import com.bws.musclefood.R
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FeatchDataActivity : AppCompatActivity() {

    lateinit var database: ContactDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_featch_data)

        database = ContactDatabase.getDatabase(this)

        database.contactDao().getContact().observe(this, Observer {
            Log.d("NAME==",it.toString())
            Log.d("NAME==",it[4].name)

            val gs = Gson()
            Log.d("NAME==",Gson().toJson(it))

           GlobalScope.launch {
              val ids =  database.contactDao().getMobileNo(5).toString()
               Log.d("IDDDSSS==",ids.toString())
               println("ID=="+Gson().toJson(ids))

              // val idBtween = database.contactDao().getIds(10,11).toString()

              // Log.d("IDDDSSS==",idBtween.toString())
               //println("ID=="+Gson().toJson(idBtween))
           }

        })
    }
}