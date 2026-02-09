package com.catcode.note_app.ui

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch
import com.catcode.note_app.R
import com.catcode.note_app.data.entity.NoteEntity
import com.catcode.note_app.data.repository.NoteRepository
import com.catcode.note_app.util.CurrencyFormatter

class AddNoteDialog(
  private val context: Context,
  private val repository: NoteRepository,
  private val lifecycleScope: LifecycleCoroutineScope,
  private val onSuccess: () -> Unit
) {

  private fun setupDatePicker(inputDate: EditText) {
    val calendar = Calendar.getInstance()

    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // default
    inputDate.setText(formatter.format(calendar.time))

    inputDate.setOnClickListener {
      DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
          calendar.set(year, month, dayOfMonth)
          inputDate.setText(formatter.format(calendar.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
      ).show()
    }
  }


  fun show() {
    val view = android.view.LayoutInflater.from(context)
      .inflate(R.layout.dialog_add_note, null)

    val inputName = view.findViewById<EditText>(R.id.inputName)
    val inputDate = view.findViewById<EditText>(R.id.inputDate)
    val inputAddress = view.findViewById<EditText>(R.id.inputAddress)
    val inputPrice = view.findViewById<EditText>(R.id.inputPrice)
    val inputStatus = view.findViewById<EditText>(R.id.inputStatus)

    setupDatePicker(inputDate)
    setupCurrencyFormatter(inputPrice)

    setupCurrencyFormatter(inputPrice)

    AlertDialog.Builder(context)
      .setTitle("Tambah Catatan")
      .setView(view)
      .setPositiveButton("Simpan") { _, _ ->

        val rawPrice = CurrencyFormatter.extractRawValue(
          inputPrice.text.toString()
        )

        val note = NoteEntity(
          name = inputName.text.toString(),
          date = inputDate.text.toString(),
          address = inputAddress.text.toString(),
          price = rawPrice,
          status = inputStatus.text.toString()
        )

        lifecycleScope.launch {
          repository.insertNote(note)
          onSuccess()
        }
      }
      .setNegativeButton("Batal", null)
      .show()
  }

  private fun setupCurrencyFormatter(inputPrice: EditText) {
    var current = ""

    inputPrice.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(
        s: CharSequence?, start: Int, count: Int, after: Int
      ) {}

      override fun afterTextChanged(s: Editable?) {}

      override fun onTextChanged(
        s: CharSequence?, start: Int, before: Int, count: Int
      ) {
        if (s.toString() != current) {
          inputPrice.removeTextChangedListener(this)

          val formatted = CurrencyFormatter.formatRupiah(s.toString())
          current = formatted

          inputPrice.setText(formatted)
          inputPrice.setSelection(formatted.length)

          inputPrice.addTextChangedListener(this)
        }
      }
    })
  }
}
