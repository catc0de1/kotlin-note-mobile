package com.catcode.note_app.model

data class Note(
  val id: Int,
  val nama: String,
  val date: String,
  val alamat: String,
  val harga: Int,
  val status: Boolean
)
