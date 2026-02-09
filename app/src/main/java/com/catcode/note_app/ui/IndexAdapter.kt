package com.catcode.note_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.catcode.note_app.R
import com.catcode.note_app.data.entity.NoteEntity

class IndexAdapter(
  private var count: Int,
  private val onIndexSelected: (Int) -> Unit,
  private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<IndexAdapter.VH>() {

  private var selectedPosition = -1

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

    val bg = when {
      position == selectedPosition ->
        R.drawable.cell_index_selected
      position % 2 == 0 ->
        R.drawable.cell_index_even
      else ->
        R.drawable.cell_index_odd
    }

    holder.itemView.setBackgroundResource(bg)

    holder.itemView.setOnClickListener {
      select(position)
    }

    holder.itemView.setOnLongClickListener {
      select(position)

      val popup = PopupMenu(holder.itemView.context, holder.itemView)
      popup.inflate(R.menu.menu_note_row)

      popup.setOnMenuItemClickListener { item ->
        when (item.itemId) {
          R.id.action_edit -> {
            // TODO: UI only (logic nanti)
            true
          }
          R.id.action_delete -> {
            onDelete(position)
            true
          }
          else -> false
        }
      }

      popup.show()
      true
    }
  }

  override fun getItemCount() = count

  fun updateCount(newCount: Int) {
    count = newCount
    notifyDataSetChanged()
  }

  fun setSelectedPosition(position: Int) {
    val oldPos = selectedPosition
    selectedPosition = position

    if (oldPos != -1) notifyItemChanged(oldPos)
    if (position != -1) notifyItemChanged(position)
  }

  fun clearSelection() {
    val oldPos = selectedPosition
    selectedPosition = -1
    if (oldPos != -1) notifyItemChanged(oldPos)
  }

  private fun select(position: Int) {
    val oldPos = selectedPosition
    selectedPosition = position

    if (oldPos != -1) notifyItemChanged(oldPos)
    notifyItemChanged(position)

    onIndexSelected(position)
  }
}
