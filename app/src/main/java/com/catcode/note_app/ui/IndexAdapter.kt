package com.catcode.note_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.catcode.note_app.R

class IndexAdapter(private var count: Int) :
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

    val bg = if (position % 2 == 0)
        R.drawable.cell_index_even
    else
        R.drawable.cell_index_odd

    holder.itemView.setBackgroundResource(bg)
  }

  override fun getItemCount() = count

  fun updateCount(newCount: Int) {
    count = newCount
    notifyDataSetChanged()
  }
}
