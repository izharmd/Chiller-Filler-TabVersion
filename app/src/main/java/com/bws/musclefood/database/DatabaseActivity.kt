/*
package com.bws.musclefood.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.bws.musclefood.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class DatabaseActivity : AppCompatActivity() {
    lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val exfun = MyExtensionFun().myExtenFun2()
        val myString1 = "Hello Everyone"
        val result = myString1.myString2()
        val myst = myString1.removeFirstLastChar()

        val list = ArrayList<Contact>()

        val random = Random().nextInt()

        list.add(Contact("Izan", "9931314095",1))
        list.add(Contact("Izan", "9931314095",1))
        list.add(Contact("Izan", "9931314095",2))



        val dog = ArrayList<Dog>()


        dog.add(Dog(0, "Nusrat dog"))
        dog.add(Dog(1, "Izhar dog"))
        dog.add(Dog(2, "Izan dog"))


        val dogOwner = ArrayList<Owner>()

        dogOwner.add(Owner(0, "Nusrat"))
        dogOwner.add(Owner(1, "Izhar"))
        dogOwner.add(Owner(2, "Izan"))



       // database = AppDatabase.getDatabase(this)

        GlobalScope.launch {
            database.contactDao().addDistrict(list)



         database.contactDao().getContact().observe(this@DatabaseActivity, Observer {

             val contact2 = ArrayList<Contact2>()
                for(i in 1..it.size){
                    contact2.add(Contact2(it[i].name,it[i].phone))
                }
            })



            val dogData = database.contactDao().getDogsAndOwners().toString()
            Log.d("NAME==", dogData.toString())

            val dt = database.contactDao().getMobileNo1()

        }

        */
/*  database.contactDao().getContact().observe(this, Observer {
              Log.d("NAME==",it.toString())
              Log.d("NAME==",it[4].name)
          })*//*


        GlobalScope.launch {

        }
    }


    fun getData(view: View) {

        startActivity(Intent(this, FeatchDataActivity::class.java))
    }
}*/
