package com.meezu.persistentstorageassignment.database

import androidx.room.*
import com.meezu.persistentstorageassignment.features.entity.Note
import com.meezu.persistentstorageassignment.utils.constants.DBConstants

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Query("SELECT * FROM ${DBConstants.tableName}")
    fun getAllNotes(): List<Note>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: Note)

    @Query("DELETE FROM ${DBConstants.tableName} WHERE noteId = :noteId")
    fun deleteNote(noteId: Int)
}