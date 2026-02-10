package com.catcode.note_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.catcode.note_app.data.entity.NoteEntity
import com.catcode.note_app.data.repository.NoteRepository

class NoteViewModel(
  private val repository: NoteRepository
) : ViewModel() {

  private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
  val notes: StateFlow<List<NoteEntity>> = _notes

  fun loadNotes() {
    viewModelScope.launch {
      _notes.value = repository.getAllNotes()
    }
  }

  fun deleteNote(note: NoteEntity) {
    viewModelScope.launch {
      repository.deleteNote(note)
      loadNotes()
    }
  }

  fun insertNote(note: NoteEntity) {
    viewModelScope.launch {
      repository.insertNote(note)
      loadNotes()
    }
  }

  fun updateNote(note: NoteEntity) {
    viewModelScope.launch {
      repository.updateNote(note)
      loadNotes()
    }
  }

  fun exportNotes(
    onResult: (List<NoteEntity>) -> Unit
  ) {
    viewModelScope.launch {
      val notes = repository.getAllNotesForExport()
      onResult(notes)
    }
  }
}
