package com.meezu.persistentstorageassignment.features.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.meezu.persistentstorageassignment.R
import com.meezu.persistentstorageassignment.database.AppDatabase
import com.meezu.persistentstorageassignment.features.entity.Note
import com.meezu.persistentstorageassignment.utils.constants.BundleConstants
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity(), View.OnClickListener {

    private var isEdit = false
    private lateinit var notetoUpdate : Note
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        checkIfAddOrEdit()
        initListener()
    }

    private fun initListener(){
        btnSave?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0){
            btnSave -> {
                val title = edtTitle.text.toString()
                val description = edtDescription.text.toString()

                if (title == "" || description == "") {
                    Toast.makeText(this, "Note title and  description cannot be empty.", Toast.LENGTH_LONG).show()

                } else {
                    if (isEdit) {
                        val note = Note(noteId = noteId, noteTitle = title, description = description)
                        updateNote(note)
                    }else {

                        val note = Note(noteId = noteId, noteTitle = title, description = description )
                        addNote(note)
                    }
                }
            }
        }
    }

    private fun checkIfAddOrEdit(){
        isEdit = intent.getBooleanExtra(BundleConstants.isEdit, false)
        if (isEdit) {
            val noteId = intent.getIntExtra("noteId", 1)
            getNotesById(noteId)
        }
    }

    private fun addNote(note: Note) {
        getAppDatabase()?.appDao()?.insertNote(note)
        super.onBackPressed()
    }

    private fun getNotesById(noteId: Int) {
        val noteList = getAppDatabase()?.appDao()?.getAllNotes()

        if (!noteList.isNullOrEmpty()){
            for (i in noteList.indices){
                if (noteList[i].noteId == noteId){
                    showNoteDetailInView(noteList[i])
                }
            }
        }
    }

    private fun showNoteDetailInView(note: Note) {
        notetoUpdate = note
        noteId = note.noteId!!
        edtTitle.setText(note.noteTitle)
        edtDescription.setText(note.description)
    }

    private fun updateNote(note: Note) {
        getAppDatabase()?.appDao()?.updateNote(note)
        super.onBackPressed()
    }

    private fun getAppDatabase() = AppDatabase.getAppDatabase(applicationContext)
}