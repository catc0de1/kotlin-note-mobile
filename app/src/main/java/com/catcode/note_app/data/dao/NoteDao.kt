package com.catcode.note_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.catcode.note_app.data.entity.NoteEntity

@Dao
interface NoteDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(note: NoteEntity)

  @Query("SELECT * FROM notes ORDER BY date ASC")
  suspend fun getAll(): List<NoteEntity>

  @Update
  suspend fun update(note: NoteEntity)

  @Delete
  suspend fun delete(note: NoteEntity)

  @Query("DELETE FROM notes")
  suspend fun deleteAll()

  @Query("SELECT * FROM notes WHERE name LIKE '%' || :keyword || '%' ORDER BY date ASC")
  suspend fun searchByName(keyword: String): List<NoteEntity>
}
