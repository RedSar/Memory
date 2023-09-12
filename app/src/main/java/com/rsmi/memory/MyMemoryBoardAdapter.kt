package com.rsmi.memory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyMemoryBoardAdapter(private val context: Context, private val numPieces: Int) :
    RecyclerView.Adapter<MyMemoryBoardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView:View  = LayoutInflater.from(context).inflate(R.layout.memory_card,parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = numPieces
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(position: Int) {

        }

    }

}


