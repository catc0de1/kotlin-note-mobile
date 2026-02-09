package com.catcode.note_app.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {

  fun formatRupiah(raw: String): String {
    if (raw.isEmpty()) return ""

    val clean = raw.replace("[^\\d]".toRegex(), "")
    if (clean.isEmpty()) return ""

    val value = clean.toLongOrNull() ?: return ""
    val formatter = NumberFormat.getInstance(Locale("in", "ID"))

    return "Rp ${formatter.format(value)}"
  }

  fun extractRawValue(formatted: String): Int {
    return formatted.replace("[^\\d]".toRegex(), "")
      .toIntOrNull() ?: 0
  }
}
