package com.catcode.note_app.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.catcode.note_app.R

object ConfirmResetDialog {

  fun show(
    context: Context,
    onConfirm: () -> Unit
  ) {
    AlertDialog.Builder(context)
      .setTitle("Hapus Semua Data")
      .setMessage(
        "Aksi ini akan menghapus semua data secara permanen.\n\n" +
        "Aksi ini tidak dapat dikembalikan."
      )
      .setPositiveButton("Delete") { _, _ ->
        onConfirm()
      }
      .setNegativeButton("Cancel", null)
      .setCancelable(true)
      .show()
  }
}
