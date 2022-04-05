package com.bws.musclefood

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.profile.ProfileAdapter
import kotlinx.android.synthetic.main.a_profile_new.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.side_menu_drawer.*
import kotlin.collections.ArrayList

class NavigationActivity : AppCompatActivity() {

    var items: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navLV.layoutManager = LinearLayoutManager(this)

        drawer_layout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
                //items.clear()
                items.add(Item("wertyuiokpl;"))
               /* items.add(Item("wertyuiokpl;"))
                items.add(Item("wertyuiokpl;"))
                items.add(Item("wertyuiokpl;"))
                items.add(Item("wertyuiokpl;"))
                items.add(Item("wertyuiokpl;"))
*/
             /*   val adapter = NavigationAdapter(items)
                navLV.adapter = adapter
                adapter.notifyDataSetChanged()*/
            }

            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {

            }
        })
    }

}