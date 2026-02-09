package com.catcode.note_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.catcode.note_app.R
import com.catcode.note_app.data.entity.NoteEntity
import com.catcode.note_app.util.CurrencyFormatter

class NoteAdapter(
    private val notes: MutableList<NoteEntity>,
    private val onRowSelected: (Int) -> Unit,
    // private val onEdit: (Int) -> Unit,
    // private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

  private var selectedPosition = -1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // val id: TextView = view.findViewById(R.id.textId)
        val name: TextView = view.findViewById(R.id.textName)
        val date: TextView = view.findViewById(R.id.textDate)
        val address: TextView = view.findViewById(R.id.textAddress)
        val priceLabel: TextView = view.findViewById(R.id.textPriceLabel)
        val priceValue: TextView = view.findViewById(R.id.textPriceValue)
        val status: TextView = view.findViewById(R.id.textStatus)
        // val action: ImageButton = view.findViewById(R.id.btnAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val note = notes[position]

      val bg = when {
        position == selectedPosition ->
          R.drawable.cell_row_selected
        position % 2 == 0 ->
          R.drawable.cell_row_even
        else ->
          R.drawable.cell_row_odd
      }

      holder.itemView.setBackgroundResource(bg)

        // holder.id.text = note.id.toString()
        holder.name.text = note.name
        holder.date.text = note.date
        holder.address.text = note.address
        holder.status.text = note.status

        holder.priceLabel.text = "Rp"
        holder.priceValue.text =
          CurrencyFormatter.formatRupiah(note.price.toString())
            .replace("Rp ", "")

        // holder.action.setOnClickListener {
            // nanti bisa popup menu (Edit / Delete)
            // onEdit(position)
        // }

      holder.itemView.setOnClickListener {
        val oldPos = selectedPosition
        selectedPosition = position

        if (oldPos != -1) notifyItemChanged(oldPos)
        notifyItemChanged(position)

        onRowSelected(position)
      }

      holder.itemView.setOnLongClickListener {
        val oldPos = selectedPosition
        selectedPosition = position

        if (oldPos != -1) notifyItemChanged(oldPos)
        notifyItemChanged(position)

        onRowSelected(position)

        val popup = PopupMenu(holder.itemView.context, holder.itemView)
        popup.inflate(R.menu.menu_note_row)

        popup.setOnMenuItemClickListener { item ->
          when (item.itemId) {
            R.id.action_edit -> {
              // TODO: UI only (logic nanti)
              true
            }
            R.id.action_delete -> {
              // TODO: UI only (logic nanti)
              true
            }
            else -> false
          }
        }

        popup.show()
        true
      }
    }

    override fun getItemCount(): Int = notes.size

    fun submitData(newNotes: List<NoteEntity>) {
      notes.clear()
      notes.addAll(newNotes)
      notifyDataSetChanged()
    }

  fun clearSelection() {
    val oldPos = selectedPosition
    selectedPosition = -1
    if (oldPos != -1) notifyItemChanged(oldPos)
  }

  fun hasSelection(): Boolean =selectedPosition != -1

  fun setSelectedPosition(position: Int) {
    val oldPos = selectedPosition
    selectedPosition = position

    if (oldPos != -1) notifyItemChanged(oldPos)
    if (position != -1) notifyItemChanged(position)
  }
}
