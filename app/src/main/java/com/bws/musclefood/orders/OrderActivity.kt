package com.bws.musclefood.orders

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bws.musclefood.R
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.orders.fragment.MyAdapter
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.viewmodels.AddNewAddressViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.tool_bar_address.*


class OrderActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        supportActionBar?.hide()
        txtTxtHeader.text = "Orders"
        imvSaveaddress.visibility = View.GONE

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tabLayout.addTab(tabLayout.newTab().setText("Current/Open Order"))
        tabLayout.addTab(tabLayout.newTab().setText("Recent Order"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = MyAdapter(
            this, supportFragmentManager,
            tabLayout.tabCount
        )
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        imvBack.setOnClickListener() {
            finish()
        }
    }



}