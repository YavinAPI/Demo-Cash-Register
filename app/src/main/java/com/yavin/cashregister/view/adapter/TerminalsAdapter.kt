package com.yavin.cashregister.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yavin.cashregister.R
import com.yavin.cashregister.model.TerminalServiceDTO
import java.lang.Exception

class TerminalsAdapter(private val onClick: (TerminalServiceDTO) -> Unit) :
    RecyclerView.Adapter<TerminalsAdapter.ViewHolder>() {

    private var dataProvider: List<TerminalServiceDTO> = emptyList()

    class ViewHolder(itemView: View, val onClick: (TerminalServiceDTO) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private var currentTerminal: TerminalServiceDTO? = null
        val textView: TextView = itemView.findViewById(R.id.terminal_name_label)

        init {
            itemView.setOnClickListener {
                currentTerminal?.let {
                    onClick(it)
                }
            }
        }

        fun bind(terminalDTO: TerminalServiceDTO) {
            currentTerminal = terminalDTO
            textView.text = terminalDTO.name
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.terminal_row_item_layout, viewGroup, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.bind(dataProvider[position])
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return dataProvider.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataProvider(value: List<TerminalServiceDTO>) {
        dataProvider = value
        notifyDataSetChanged()
    }

}