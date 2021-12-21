package com.bws.musclefood.enotes

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bws.musclefood.enotes.eNoteFragments.CreateNewNotesFrag
import com.bws.musclefood.enotes.eNoteFragments.ViewAllNotesFrag
import com.bws.musclefood.enotes.eNoteFragments.ViewCloseEnotesFrag
import com.bws.musclefood.enotes.eNoteFragments.ViewOpenEnotesFrag

@Suppress("DEPRECATION")
internal class TabsAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CreateNewNotesFrag()
            }
            1 -> {
                ViewAllNotesFrag()
            }
            2 -> {
                ViewOpenEnotesFrag()
            }
            3 -> {
              ViewCloseEnotesFrag()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}