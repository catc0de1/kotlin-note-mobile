package com.catcode.note_app

import android.os.Bundle
// import androidx.activity.ComponentActivity
// import androidx.activity.compose.setContent
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.HorizontalScrollView
import android.widget.EditText
import android.view.MotionEvent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.catcode.note_app.R
import com.catcode.note_app.ui.NoteAdapter
import com.catcode.note_app.ui.IndexAdapter
import com.catcode.note_app.ui.TwoDScrollView
import com.catcode.note_app.ui.AddNoteDialog
import com.catcode.note_app.data.db.AppDatabase
import com.catcode.note_app.data.repository.NoteRepository
import com.catcode.note_app.data.entity.NoteEntity

class MainActivity : AppCompatActivity() {

  private lateinit var repository: NoteRepository
  private lateinit var noteAdapter: NoteAdapter
  private lateinit var indexAdapter: IndexAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val mainRv = findViewById<RecyclerView>(R.id.recyclerView)
    val indexRv = findViewById<RecyclerView>(R.id.indexRecycler)

    val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

    val tableScroll = findViewById<TwoDScrollView>(R.id.tableScroll)
    val headerScroll = findViewById<HorizontalScrollView>(R.id.headerScroll)

    val database = AppDatabase.getInstance(this)
    repository = NoteRepository(database.noteDao())

    noteAdapter = NoteAdapter(mutableListOf())
    indexAdapter = IndexAdapter(0)

    mainRv.layoutManager = LinearLayoutManager(this)
    indexRv.layoutManager = LinearLayoutManager(this)

    mainRv.adapter = noteAdapter
    indexRv.adapter = indexAdapter

    loadNotes()

    tableScroll.indexRecycler = indexRv
    tableScroll.headerScroll = headerScroll

    fabAdd.setOnClickListener {
      openAddNoteDialog()
    }
  }

    private fun loadNotes() {
      lifecycleScope.launch {
        val notes = repository.getAllNotes()
        noteAdapter.submitData(notes)
        indexAdapter.updateCount(notes.size)
      }
    }

    private fun openAddNoteDialog() {
    AddNoteDialog(
      context = this,
      repository = repository,
      lifecycleScope = lifecycleScope,
      onSuccess = { loadNotes() }
    ).show()
  }
}
