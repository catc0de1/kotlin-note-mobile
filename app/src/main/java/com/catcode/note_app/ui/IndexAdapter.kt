package com.catcode.note_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.catcode.note_app.R

class IndexAdapter(private val count: Int) :
  RecyclerView.Adapter<IndexAdapter.VH>() {

  class VH(view: View) : RecyclerView.ViewHolder(view) {
    val text: TextView = view as TextView
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_index_row, parent, false)
    return VH(view)
  }

  override fun onBindViewHolder(holder: VH, position: Int) {
    holder.text.text = (position + 1).toString()
  }

  override fun getItemCount() = count
}
