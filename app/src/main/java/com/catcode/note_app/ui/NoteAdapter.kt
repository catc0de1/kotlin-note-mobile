package com.catcode.note_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.catcode.note_app.R
import com.catcode.note_app.data.entity.NoteEntity
import com.catcode.note_app.util.CurrencyFormatter

class NoteAdapter(
    private val notes: MutableList<NoteEntity>,
    // private val onEdit: (Int) -> Unit,
    // private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

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

        val bg = if (position % 2 == 0)
            R.drawable.cell_row_even
        else
            R.drawable.cell_row_odd

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
    }

    override fun getItemCount(): Int = notes.size

    fun submitData(newNotes: List<NoteEntity>) {
      notes.clear()
      notes.addAll(newNotes)
      notifyDataSetChanged()
    }
}
