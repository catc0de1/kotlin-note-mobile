package com.catcode.note_app.data.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.catcode.note_app.data.dao.NoteDao
import com.catcode.note_app.data.entity.NoteEntity

class NoteRepository(private val noteDao: NoteDao) {

  suspend fun getAllNotes(): List<NoteEntity> {
    return noteDao.getAll()
  }

  suspend fun getNotes(
    keyword: String?,
    sortAsc: Boolean
  ): List<NoteEntity> {

    val order = if (sortAsc) "ASC" else "DESC"

    val baseQuery = StringBuilder()
    val args = mutableListOf<Any>()

    baseQuery.append("SELECT * FROM notes ")

    if (!keyword.isNullOrBlank()) {
      baseQuery.append("WHERE name LIKE ? ")
      args.add("%$keyword%")
    }

    baseQuery.append("ORDER BY date $order")

    val query = SimpleSQLiteQuery(baseQuery.toString(), args.toTypedArray())

    return noteDao.getNotes(query)
  }


  suspend fun insertNote(note: NoteEntity) {
    noteDao.insert(note)
  }

  suspend fun updateNote(note: NoteEntity) {
    noteDao.update(note)
  }

  suspend fun deleteNote(note: NoteEntity) {
    noteDao.delete(note)
  }

  suspend fun getAllNotesForExport(): List<NoteEntity> {
    return noteDao.getAll()
  }

  suspend fun deleteAllNotes() {
    noteDao.deleteAll()
  }

  suspend fun searchNotesByName(keyword: String): List<NoteEntity> {
    return noteDao.searchByName(keyword)
  }
}
