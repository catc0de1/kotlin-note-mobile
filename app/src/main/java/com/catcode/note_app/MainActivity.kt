package com.catcode.note_app

import android.os.Bundle
// import androidx.activity.ComponentActivity
// import androidx.activity.compose.setContent
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catcode.note_app.model.Note
import com.catcode.note_app.ui.NoteAdapter

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

    recyclerView.layoutManager =
      object : LinearLayoutManager(this) {
        override fun canScrollVertically() = false
        override fun canScrollHorizontally() = false
      }

    recyclerView.apply {
      isNestedScrollingEnabled = false
      overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    val notes = mutableListOf(
      Note(1, "Andi", "2024-01-01", "Surabaya", 50000, true),
      Note(2, "Budi", "2024-01-02", "Malang", 75000, false),
      Note(3, "Citra", "2024-01-03", "Sidoarjo", 60000, true),
      Note(4, "Dewi", "2024-01-04", "Gresik", 45000, false),
      Note(5, "Eko", "2024-01-05", "Pasuruan", 82000, true),
      Note(6, "Fajar", "2024-01-06", "Blitar", 30000, true),
      Note(7, "Gilang", "2024-01-07", "Kediri", 91000, false),
      Note(8, "Hani", "2024-01-08", "Jombang", 70000, true),
      Note(9, "Irfan", "2024-01-09", "Lamongan", 52000, false),
      Note(10, "Joko", "2024-01-10", "Tuban", 65000, true),
      Note(11, "Kiki", "2024-01-11", "Mojokerto", 48000, false),
      Note(12, "Lina", "2024-01-12", "Probolinggo", 88000, true),
      Note(13, "Maya", "2024-01-13", "Banyuwangi", 99000, true),
      Note(14, "Nanda", "2024-01-14", "Lumajang", 56000, false),
      Note(15, "Oki", "2024-01-15", "Bondowoso", 43000, true),
      Note(16, "Putri", "2024-01-16", "Situbondo", 61000, false),
      Note(17, "Rizki", "2024-01-17", "Jember", 72000, true),
      Note(18, "Sari", "2024-01-18", "Magetan", 39000, false),
      Note(19, "Tono", "2024-01-19", "Ngawi", 84000, true),
      Note(20, "Wulan", "2024-01-20", "Pacitan", 47000, false),
      Note(21, "Siti", "2024-01-21", "Babat", 57000, true),
      Note(22, "Supriadi", "2024-01-22", "Madura", 78000, false),
      Note(23, "Ningsih", "2024-01-23", "Tulungagung", 41000, false),
      Note(24, "Jumadi", "2024-01-24", "Boerno", 40000, true),
      Note(25, "Agus", "2024-01-25", "Duduk", 70000, false),
    )

    recyclerView.adapter = NoteAdapter(notes)
  }
}
