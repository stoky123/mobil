package com.example.android.navigation.database

import com.example.android.navigation.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NumberAdapter : RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {

    private var data = emptyList<Number>()

    override fun getItemCount() = data.size

    class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.findViewById<TextView>(R.id.counter_id).text = (position + 1).toString()
        holder.itemView.findViewById<TextView>(R.id.text_input).text = item.numberValue.toString();

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        return NumberViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.database_row,
                parent,
                false
            )
        )

    }

    fun setData(data: List<Number>) {
        this.data = data
        notifyDataSetChanged()
    }

}