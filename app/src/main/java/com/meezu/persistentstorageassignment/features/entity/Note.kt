package com.meezu.persistentstorageassignment.features.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.meezu.persistentstorageassignment.utils.constants.DBConstants

@Entity(tableName = DBConstants.tableName)
data class Note (
    @ColumnInfo(name = "note")
    var noteTitle: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @PrimaryKey(autoGenerate = true)
    var noteId: Int? = 0,

)