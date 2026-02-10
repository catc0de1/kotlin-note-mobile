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
import androidx.lifecycle.ViewModelProvider
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.coroutines.launch
import android.widget.HorizontalScrollView
import android.widget.EditText
import android.widget.ImageView
import android.view.MotionEvent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.catcode.note_app.R
import com.catcode.note_app.ui.NoteAdapter
import com.catcode.note_app.ui.IndexAdapter
import com.catcode.note_app.ui.TwoDScrollView
import com.catcode.note_app.ui.NoteViewModel
import com.catcode.note_app.ui.NoteViewModelFactory
import com.catcode.note_app.ui.dialog.MoreActionDialog
import com.catcode.note_app.ui.dialog.AddNoteDialog
import com.catcode.note_app.ui.dialog.EditNoteDialog
import com.catcode.note_app.ui.dialog.ConfirmDeleteDialog
import com.catcode.note_app.data.db.AppDatabase
import com.catcode.note_app.data.repository.NoteRepository
import com.catcode.note_app.data.entity.NoteEntity

class MainActivity : AppCompatActivity() {

  private lateinit var viewModel: NoteViewModel
  private lateinit var repository: NoteRepository
  private lateinit var noteAdapter: NoteAdapter
  private lateinit var indexAdapter: IndexAdapter

  private val exportCsvLauncher =
    registerForActivityResult(
      androidx.activity.result.contract.ActivityResultContracts.CreateDocument("text/csv")
  ) { uri ->
    if (uri != null) {
      exportCsv(uri)
    }
  }

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

    val factory = NoteViewModelFactory(repository)
    viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

    val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
    val moreActionMenu = findViewById<ImageView>(R.id.moreActionMenu)

    // noteAdapter = NoteAdapter(mutableListOf())
    noteAdapter = NoteAdapter(
      mutableListOf(),
      onRowSelected = {
        position -> indexAdapter.setSelectedPosition(position)
      },
      onEdit = {
        note -> EditNoteDialog(
          context = this,
          note = note,
          onSubmit = {
            updated -> viewModel.updateNote(updated)
          }
        ).show()
      },
      onDelete = {
        note -> ConfirmDeleteDialog.show(
          context = this,
          onConfirm = {
            viewModel.deleteNote(note)
          }
        )
      }
    )

    indexAdapter = IndexAdapter(
      0,
      onIndexSelected = {
        position -> noteAdapter.setSelectedPosition(position)
      },
      onEdit ={
        position -> val note = viewModel.notes.value.getOrNull(position)
          ?: return@IndexAdapter

        EditNoteDialog(
          context = this,
          note = note,
          onSubmit = {
            updated -> viewModel.updateNote(updated)
          }
        ).show()
      },
      onDelete = {
        position -> val note = viewModel.notes.value.getOrNull(position)
          ?: return@IndexAdapter

        ConfirmDeleteDialog.show(
          context = this,
          onConfirm = {
            viewModel.deleteNote(note)
          }
        )
      }
    )

    mainRv.layoutManager = LinearLayoutManager(this)
    indexRv.layoutManager = LinearLayoutManager(this)

    mainRv.adapter = noteAdapter
    indexRv.adapter = indexAdapter

    observeNotes()
    viewModel.loadNotes()

    tableScroll.indexRecycler = indexRv
    tableScroll.headerScroll = headerScroll

    headerScroll.setOnTouchListener { _, event ->
      tableScroll.dispatchTouchEvent(event)
      true
    }

    indexRv.setOnTouchListener { _, event ->
      tableScroll.dispatchTouchEvent(event)
      true
    }

    fabAdd.setOnClickListener {
      openAddNoteDialog()
    }

    MoreActionDialog(
      activity = this,
      drawerLayout = drawerLayout,
      onExport = {
        exportCsvLauncher.launch("notes-${System.currentTimeMillis()}.csv")
      }
    ).bind(moreActionMenu)
  }

    private fun observeNotes() {
      lifecycleScope.launch {
        viewModel.notes.collect { notes ->
          noteAdapter.submitData(notes)
          indexAdapter.updateCount(notes.size)
        }
      }
    }

    private fun openAddNoteDialog() {
    AddNoteDialog(
      context = this,
      repository = repository,
      lifecycleScope = lifecycleScope,
      onSuccess = {
        viewModel.loadNotes()
      }
    ).show()
  }


  override fun onBackPressed() {
    if (noteAdapter.hasSelection()) {
      noteAdapter.clearSelection()
      indexAdapter.clearSelection()
    } else {
      super.onBackPressed()
    }
  }

  private fun exportCsv(uri: android.net.Uri) {
    viewModel.exportNotes { notes ->
      contentResolver.openOutputStream(uri)?.let { output ->
        com.catcode.note_app.util.CsvExporter.writeNotesToCsv(
          notes = notes,
          outputStream = output
        )
      }
    }
  }

}
