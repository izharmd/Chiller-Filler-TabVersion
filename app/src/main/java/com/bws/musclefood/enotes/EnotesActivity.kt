package com.bws.musclefood.enotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import com.bws.musclefood.orders.OrderActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_e_notes.*
import kotlinx.android.synthetic.main.activity_search_order.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class EnotesActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_notes)
        supportActionBar?.hide()
        txtTxtHeader.text = "eNotes"
        imvSaveaddress.visibility = View.GONE


       /* tabLayout.addTab(tabLayout.newTab().setText("Create New eNotes").setIcon(R.drawable.ic_home_24))
        tabLayout.addTab(tabLayout.newTab().setText("View All eNotes").setIcon(R.drawable.ic_chat_24))
        tabLayout.addTab(tabLayout.newTab().setText("View Open eNotes").setIcon(R.drawable.ic_call_24))
        tabLayout.addTab(tabLayout.newTab().setText("View Close eNotes").setIcon(R.drawable.ic_call_24))*/

        tabLayout.addTab(tabLayout.newTab().setText("Create New eNotes").setIcon(R.drawable.enotesicon))
        tabLayout.addTab(tabLayout.newTab().setText("View All eNotes").setIcon(R.drawable.enotesicon1))
        tabLayout.addTab(tabLayout.newTab().setText("View Open eNotes").setIcon(R.drawable.enotesicon2))
        tabLayout.addTab(tabLayout.newTab().setText("View Close eNotes").setIcon(R.drawable.enotesicon3))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = TabsAdapter(this, supportFragmentManager,
            tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })



        imvBack.setOnClickListener(){
            finish()
        }

    }
}