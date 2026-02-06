package com.catcode.note_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.catcode.note_app.R
import com.catcode.note_app.model.Note

class NoteAdapter(
    private val notes: MutableList<Note>,
    private val onEdit: (Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // val id: TextView = view.findViewById(R.id.textId)
        val nama: TextView = view.findViewById(R.id.textNama)
        val date: TextView = view.findViewById(R.id.textDate)
        val alamat: TextView = view.findViewById(R.id.textAlamat)
        val harga: TextView = view.findViewById(R.id.textHarga)
        val status: TextView = view.findViewById(R.id.textStatus)
        val action: ImageButton = view.findViewById(R.id.btnAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        // holder.id.text = note.id.toString()
        holder.nama.text = note.nama
        holder.date.text = note.date
        holder.alamat.text = note.alamat
        holder.harga.text = note.harga.toString()
        holder.status.text = if (note.status) "Aktif" else "Nonaktif"

        holder.action.setOnClickListener {
            // nanti bisa popup menu (Edit / Delete)
            onEdit(position)
        }
    }

    override fun getItemCount(): Int = notes.size
}
