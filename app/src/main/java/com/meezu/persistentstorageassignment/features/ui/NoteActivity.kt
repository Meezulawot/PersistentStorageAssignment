package com.meezu.persistentstorageassignment.features.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekbana.roomsession.feature.OnEditDeleteListener
import com.meezu.persistentstorageassignment.R
import com.meezu.persistentstorageassignment.database.AppDatabase
import com.meezu.persistentstorageassignment.features.adapter.NoteAdapter
import com.meezu.persistentstorageassignment.features.entity.Note
import com.meezu.persistentstorageassignment.utils.constants.BundleConstants
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var noteList : MutableList<Note>
    var adapter : NoteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        initListener()
        initRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        getNoteListFromDb()
    }

    private fun initListener() {
        fabAdd?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            fabAdd -> {
                startActivity(Intent(this, AddNoteActivity::class.java))
            }
        }
    }

    private fun getAppDatabase() = AppDatabase.getAppDatabase(applicationContext)

    @SuppressLint("NotifyDataSetChanged")
    private fun getNoteListFromDb() {
        val noteListFromDb = getAppDatabase()?.appDao()?.getAllNotes()
        this.noteList.clear()
        this.noteList.addAll(noteListFromDb as MutableList<Note>)

        Log.d("noteListfromdb", noteList.size.toString())
        adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerView() {

        rcvNotes.layoutManager = LinearLayoutManager(this)
        noteList = ArrayList()
        adapter = NoteAdapter(noteList, object : OnEditDeleteListener {
            override fun onEdit(position: Int) {
                val noteId = noteList[position].noteId
                val intent = Intent(this@NoteActivity, AddNoteActivity::class.java)
                intent.putExtra(BundleConstants.isEdit, true)
                intent.putExtra("noteId", noteId)
                startActivity(intent)
            }

            override fun onDelete(position: Int) {
                val builder = AlertDialog.Builder(this@NoteActivity)
                builder.setTitle("Delete")
                builder.setMessage("Are you sure you want to delete ${noteList[position].noteTitle} ??")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Yes") { _, _ ->
                    val noteId = noteList[position].noteId
                    deleteNote(noteId!!)
                }
                builder.setNegativeButton("No") { _, _ ->
                    Toast.makeText(this@NoteActivity, "Action cancelled", Toast.LENGTH_SHORT).show()
                }
                builder.create()
                builder.setCancelable(false)
                builder.show()

            }
        })
        rcvNotes.adapter = adapter
    }

    private fun deleteNote(noteId: Int) {
        getAppDatabase()?.appDao()?.deleteNote(noteId)
        getNoteListFromDb()
    }

}