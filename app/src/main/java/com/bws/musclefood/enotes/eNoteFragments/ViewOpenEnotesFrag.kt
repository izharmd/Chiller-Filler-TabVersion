package com.bws.musclefood.enotes.eNoteFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import kotlinx.android.synthetic.main.fragment_football.view.*
import kotlinx.android.synthetic.main.fragment_view_all_notes.view.*

class ViewOpenEnotesFrag: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_view_all_notes, container, false)
        view.recyAllNotes.layoutManager = LinearLayoutManager(context)
        val data = ArrayList<NotesModel>()

        data.add(NotesModel("Login/Registration Questions","Registering An Account","Login Question","View"))
        data.add(NotesModel("Login/Registration Questions","Registering An Account","Registration Question","View"))
        data.add(NotesModel("Login/Registration Questions","Registering An Account","Login Question","View"))

        val adapter = NotesAdapter(data)
        view.recyAllNotes.adapter = adapter
        adapter.notifyDataSetChanged()

        return view
    }
}