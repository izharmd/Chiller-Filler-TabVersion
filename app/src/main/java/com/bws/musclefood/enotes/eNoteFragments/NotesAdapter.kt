package com.bws.musclefood.enotes.eNoteFragments

import android.content.Context
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R

class NotesAdapter(val mList:ArrayList<NotesModel>):RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var context:Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_notes,parent,false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val notesModel = mList[position]
        holder.txtCategory.text = notesModel.category
        holder.txtType.text = notesModel.type
        holder.txtSubject.text = notesModel.subject
        holder.txtView.text = notesModel.message

        val content = SpannableString(notesModel.message)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        holder.txtView.text = content
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView) {
        val txtCategory:TextView = itemView.findViewById(R.id.txtCategory)
        val txtType:TextView = itemView.findViewById(R.id.txtType)
        val txtSubject:TextView = itemView.findViewById(R.id.txtSubject)
        val txtView:TextView = itemView.findViewById(R.id.txtView)

    }
}