package com.catcode.note_app

import android.os.Bundle
// import androidx.activity.ComponentActivity
// import androidx.activity.compose.setContent
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.HorizontalScrollView
import android.view.MotionEvent
import com.catcode.note_app.R
import com.catcode.note_app.model.Note
import com.catcode.note_app.ui.NoteAdapter
import com.catcode.note_app.ui.IndexAdapter
import com.catcode.note_app.ui.TwoDScrollView
import com.catcode.note_app.data.db.AppDatabase
import com.catcode.note_app.data.repository.NoteRepository
import com.catcode.note_app.data.entity.NoteEntity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val mainRv = findViewById<RecyclerView>(R.id.recyclerView)
    val indexRv = findViewById<RecyclerView>(R.id.indexRecycler)

    val tableScroll = findViewById<TwoDScrollView>(R.id.tableScroll)
    val headerScroll = findViewById<HorizontalScrollView>(R.id.headerScroll)

    val database = AppDatabase.getInstance(this)
    val repository = NoteRepository(database.noteDao())

    val noteAdapter = NoteAdapter(mutableListOf())
    val indexAdapter = IndexAdapter(0)

    mainRv.adapter = noteAdapter
    indexRv.adapter = indexAdapter

    tableScroll.indexRecycler = indexRv
    tableScroll.headerScroll = headerScroll

    mainRv.layoutManager = LinearLayoutManager(this)
    indexRv.layoutManager = LinearLayoutManager(this)

    lifecycleScope.launch {

      repository.insertNote(
        NoteEntity(
          name = "Andi",
          date = "2024-01-01",
          address = "Surabaya",
          price = 50000,
          status = "Lunas"
        )
      )

      val notes = repository.getAllNotes()
      noteAdapter.submitData(notes)
      indexAdapter.updateCount(notes.size)
    }

    mainRv.isNestedScrollingEnabled = false
    indexRv.isNestedScrollingEnabled = false

    indexRv.setOnTouchListener { _, event ->
        when (event.actionMasked) {
        MotionEvent.ACTION_DOWN,
        MotionEvent.ACTION_MOVE,
        MotionEvent.ACTION_UP -> {
            tableScroll.dispatchTouchEvent(event)
        }
    }
      // tableScroll.dispatchTouchEvent(event)
      true
    }

    // mainRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    //   override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
    //     indexRv.scrollBy(0, dy)
    //   }
    // })
  }
}
