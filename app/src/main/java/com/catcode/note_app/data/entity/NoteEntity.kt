package com.catcode.note_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(

  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,

  val name: String,
  val date: String,
  val address: String,
  val price: Int,
  val status: String
)
