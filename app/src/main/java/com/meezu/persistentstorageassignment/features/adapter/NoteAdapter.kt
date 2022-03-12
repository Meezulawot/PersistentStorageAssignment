package com.meezu.persistentstorageassignment.features.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekbana.roomsession.feature.OnEditDeleteListener
import com.meezu.persistentstorageassignment.R
import com.meezu.persistentstorageassignment.features.entity.Note
import kotlinx.android.synthetic.main.layout_custom_note.view.*

class NoteAdapter (
    val lstNote: MutableList<Note>,
    var listener: OnEditDeleteListener
): RecyclerView.Adapter<NoteAdapter.NoteVH>(){

    class NoteVH(view: View): RecyclerView.ViewHolder(view){
        var title = view.txvTitle
        var description = view.txvDescription
        var imgDelete = view.imgDelete
        var imgEdit = view.imgEdit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_custom_note, parent, false)
        return NoteVH(view)
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        val notes = lstNote[position]
        holder.title.text = notes.noteTitle
        holder.description.text = notes.description
        holder.imgDelete.setOnClickListener {
            listener.onDelete(position)
        }
        holder.imgEdit.setOnClickListener {
            listener.onEdit(position)
        }
    }

    override fun getItemCount(): Int {
        return lstNote.size
    }

}