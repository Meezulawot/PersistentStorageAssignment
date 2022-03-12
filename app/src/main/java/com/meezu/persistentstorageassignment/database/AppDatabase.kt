package com.meezu.persistentstorageassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.meezu.persistentstorageassignment.features.entity.Note
import com.meezu.persistentstorageassignment.utils.constants.DBConstants

@Database(
    entities = [(Note::class)],
    version = 3
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao() : NoteDao

    companion object{
        private var appDatabase : AppDatabase? = null

//        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL(
//                    "ALTER TABLE ${DBConstants.tableName} RENAME COLUMN id TO noteId"
//                )
//            }
//        }

        fun getAppDatabase (context : Context)=
            if (appDatabase != null){
                appDatabase
            }else{
                if (context != null) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DBConstants.databaseName
                    )
//                        .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                } else {
                    null
                }

            }


    }
}