package com.catcode.note_app.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog

object ConfirmDeleteDialog {

  fun show(
    context: Context,
    title: String = "Hapus data",
    message: String = "Apakah anda yakin ingin menghapus data ini?",
    onConfirm: () -> Unit
  ) {
    AlertDialog.Builder(context)
      .setTitle(title)
      .setMessage(message)
      .setPositiveButton("Hapus") { _, _ ->
          onConfirm()
      }
      .setNegativeButton("Batal", null)
      .show()
  }
}
