package com.example.effectivemobilepracticaltasks.topic_3_rxjava

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobilepracticaltasks.R

class ItemAdapter(private val items: List<String>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTv: TextView = itemView.findViewById(R.id.itemTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTv.text = items[position]
        holder.itemView.setOnClickListener {
            Subject.itemClickSubject.onNext(position)
        }
    }

    override fun getItemCount() = items.size
}