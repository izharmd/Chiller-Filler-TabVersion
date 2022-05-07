package com.bws.musclefood.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.room.Room
import com.bws.musclefood.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DatabaseActivity : AppCompatActivity() {
    lateinit var database: ContactDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val list = ArrayList<Contact>()

        list.add(Contact("Izan","9931314095"))
        list.add(Contact("Izhar","9931314095"))
        list.add(Contact("Mukhtar","9931314095"))
        list.add(Contact("Sarfraz","9931314095"))
        list.add(Contact("Nusrat","9931314095"))

        val dog = ArrayList<Dog>()


        dog.add(Dog(0,"Nusrat dog"))
        dog.add(Dog(1,"Izhar dog"))
        dog.add(Dog(2,"Izan dog"))


        val dogOwner = ArrayList<Owner>()

        dogOwner.add(Owner(0,"Nusrat"))
        dogOwner.add(Owner(1,"Izhar"))
        dogOwner.add(Owner(2,"Izan"))



        database = ContactDatabase.getDatabase(this)

        GlobalScope.launch {
            database.contactDao().addDistrict(list)
            //add dog
           database.contactDao().addDog(dog)
            database.contactDao().addDogOwner(dogOwner)

           val dogData = database.contactDao().getDogsAndOwners().toString()
            Log.d("NAME==",dogData.toString())

        }

      /*  database.contactDao().getContact().observe(this, Observer {
            Log.d("NAME==",it.toString())
            Log.d("NAME==",it[4].name)
        })*/
    }

    fun getData(view: View) {

        startActivity(Intent(this,FeatchDataActivity::class.java))
    }
}