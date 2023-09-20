package com.rsmi.memory

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rsmi.memory.models.BoardSize
import com.rsmi.memory.models.Card
import kotlin.math.min

class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cards: List<Card>,
    private val onClickCardListener: CardClickListener
) :
    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

    companion object {
        private const val MARGIN_SIZE = 20
        private const val TAG = "MemoryBoardAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardWith: Int = parent.width / boardSize.getNumCol() - 2 * MARGIN_SIZE
        val cardHeight: Int = parent.height / boardSize.getNumLines() - 2 * MARGIN_SIZE
        val cardDimension: Int = min(cardHeight, cardWith)

        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.memory_card, parent, false)

        val cardLayoutParams: MarginLayoutParams =
            itemView.findViewById<CardView>(R.id.cardView).layoutParams as MarginLayoutParams
        cardLayoutParams.height = cardDimension
        cardLayoutParams.width = cardDimension
        cardLayoutParams.setMargins(MARGIN_SIZE)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = boardSize.getNumPairs() * 2
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    interface CardClickListener {
        fun onClickCard(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
        fun bind(position: Int) {
            val selectedCard = cards[position]
            imageButton.setImageResource(if (selectedCard.isFaceUp) selectedCard.id else R.drawable.ic_launcher_background)

            imageButton.alpha = if (selectedCard.isMatched) .4f else 1.0f
           val colorStateList: ColorStateList? =
                if (selectedCard.isMatched) ContextCompat.getColorStateList(
                    context,
                    R.color.color_gray
                ) else null

            ViewCompat.setBackgroundTintList(imageButton, colorStateList)

            //handle onClick Event
            imageButton.setOnClickListener {
                Log.i(TAG, "Image clicked on position: $position")
                onClickCardListener.onClickCard(position)

            }
        }



    }

}


