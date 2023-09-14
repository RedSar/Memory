package com.rsmi.memory

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.rsmi.memory.models.BoardSize
import kotlin.math.min

class MyMemoryBoardAdapter(private val context: Context, private val boardSize: BoardSize) :
    RecyclerView.Adapter<MyMemoryBoardAdapter.ViewHolder>() {

    companion object {
        private const val MARGIN_SIZE = 10
        private const val TAG = "MyMemoryBoardAdapter"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardWith: Int = parent.width/boardSize.getNumCol() - 2 * MARGIN_SIZE
        val cardHeight: Int = parent.height/boardSize.getNumLines() - 2 * MARGIN_SIZE
        val cardDimension: Int = min(cardHeight,cardWith)

        val itemView:View  = LayoutInflater.from(context).inflate(R.layout.memory_card,parent, false)

        val cardLayoutParams: MarginLayoutParams = itemView.findViewById<CardView>(R.id.cardView).layoutParams as MarginLayoutParams
        cardLayoutParams.height = cardDimension
        cardLayoutParams.width = cardDimension
        cardLayoutParams.setMargins(MARGIN_SIZE)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = boardSize.getNumPairs() * 2
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
        fun bind(position: Int) {
            imageButton.setOnClickListener{
                Log.i(TAG, "Image clicked on position: $position")
            }
        }

    }

}


