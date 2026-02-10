package com.catcode.note_app.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.catcode.note_app.R
import com.catcode.note_app.data.entity.NoteEntity
import com.catcode.note_app.util.CurrencyFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditNoteDialog(
  private val context: Context,
  private val note: NoteEntity,
  private val onSubmit: (NoteEntity) -> Unit
) {

  private fun setupDatePicker(inputDate: EditText, initial: String) {
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // prefill date
    inputDate.setText(initial)

    inputDate.setOnClickListener {
      DatePickerDialog(
        context,
        { _, year, month, day ->
          calendar.set(year, month, day)
          inputDate.setText(formatter.format(calendar.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
      ).show()
    }
  }

  private fun setupCurrencyFormatter(input: EditText) {
    var current = ""

    input.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun afterTextChanged(s: Editable?) {}

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.toString() != current) {
          input.removeTextChangedListener(this)

          val formatted = CurrencyFormatter.formatRupiah(s.toString())
          current = formatted

          input.setText(formatted)
          input.setSelection(formatted.length)

          input.addTextChangedListener(this)
        }
      }
    })
  }

  fun show() {
    val view = android.view.LayoutInflater.from(context)
      .inflate(R.layout.dialog_add_note, null)

    val inputName = view.findViewById<EditText>(R.id.inputName)
    val inputDate = view.findViewById<EditText>(R.id.inputDate)
    val inputAddress = view.findViewById<EditText>(R.id.inputAddress)
    val inputPrice = view.findViewById<EditText>(R.id.inputPrice)
    val inputStatus = view.findViewById<EditText>(R.id.inputStatus)

    // PREFILL
    inputName.setText(note.name)
    inputAddress.setText(note.address)
    inputStatus.setText(note.status)
    inputPrice.setText(
      CurrencyFormatter.formatRupiah(note.price.toString())
    )

    setupDatePicker(inputDate, note.date)
    setupCurrencyFormatter(inputPrice)

    val dialog = AlertDialog.Builder(context)
      .setTitle("Edit Catatan")
      .setView(view)
      .setPositiveButton("Update", null)
      .setNegativeButton("Batal", null)
      .create()

    dialog.setOnShowListener {
      val btnUpdate = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

      btnUpdate.setOnClickListener {
        val name = inputName.text.toString().trim()
        val address = inputAddress.text.toString().trim()
        val status = inputStatus.text.toString().ifBlank { "-" }

        if (name.isEmpty()) {
          inputName.error = "Nama Wajib Diisi"
          return@setOnClickListener
        }

        if (address.isEmpty()) {
          inputAddress.error = "Alamat Wajib Diisi"
          return@setOnClickListener
        }

        val rawPrice = CurrencyFormatter.extractRawValue(
          inputPrice.text.toString()
        )

        val updated = note.copy(
          name = name,
          date = inputDate.text.toString(),
          address = address,
          price = rawPrice,
          status = status
        )

        onSubmit(updated)
        dialog.dismiss()
      }
    }

    dialog.show()
  }
}
