package com.catcode.note_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.catcode.note_app.data.repository.NoteRepository

class NoteViewModelFactory(
  private val repository: NoteRepository
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return NoteViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
