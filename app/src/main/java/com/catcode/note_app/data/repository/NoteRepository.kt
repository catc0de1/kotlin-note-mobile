package com.catcode.note_app.data.repository

import com.catcode.note_app.data.dao.NoteDao
import com.catcode.note_app.data.entity.NoteEntity

class NoteRepository(private val noteDao: NoteDao) {

  suspend fun getAllNotes(): List<NoteEntity> {
    return noteDao.getAll()
  }

  suspend fun insertNote(note: NoteEntity) {
    noteDao.insert(note)
  }

  suspend fun deleteNote(note: NoteEntity) {
    noteDao.delete(note)
  }
}
