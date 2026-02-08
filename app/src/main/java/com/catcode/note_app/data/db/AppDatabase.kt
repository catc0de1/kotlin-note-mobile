package com.catcode.note_app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.catcode.note_app.data.dao.NoteDao
import com.catcode.note_app.data.entity.NoteEntity
import com.catcode.note_app.data.db.AppDatabase

@Database(
  entities = [NoteEntity::class],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun noteDao(): NoteDao

  companion object {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "note_room.db"
        ).build().also { INSTANCE = it }
      }
    }
  }
}
