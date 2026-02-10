package com.catcode.note_app.util

import com.catcode.note_app.data.entity.NoteEntity
import java.io.OutputStream

object CsvExporter {

  fun writeNotesToCsv(
    notes: List<NoteEntity>,
    outputStream: OutputStream
  ) {
    outputStream.bufferedWriter().use { writer ->
      // Header
      writer.appendLine("ID,Name,Date,Address,Price,Status")

      // Rows
      notes.forEach { note ->
        writer.appendLine(
          "${note.id}," +
          "\"${note.name}\"," +
          "\"${note.date}\"," +
          "\"${note.address}\"," +
          note.price + "," +
          "\"${note.status}\""
        )
      }
    }
  }
}
