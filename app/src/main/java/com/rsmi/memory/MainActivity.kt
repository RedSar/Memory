package com.rsmi.memory

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rsmi.memory.models.BoardSize
import com.rsmi.memory.models.Card
import com.rsmi.memory.models.MemoryGame
import com.rsmi.memory.utils.DEFAULT_CARDS

class MainActivity : AppCompatActivity() {

    private lateinit var rvBoard : RecyclerView
    private lateinit var tvMoves : TextView
    private lateinit var tvNumPairs : TextView
    private  var boardSize : BoardSize = BoardSize.MEDIUM

    companion object{
        const val TAG = "MainActivity"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard = findViewById(R.id.rvBoard)
        tvMoves = findViewById(R.id.tvMoves)
        tvNumPairs = findViewById(R.id.tvPairs)

        val memoryGame:MemoryGame = MemoryGame(boardSize)

        rvBoard.adapter = MyMemoryBoardAdapter(this, boardSize, memoryGame.cards, object : MyMemoryBoardAdapter.CardClickListener{
            override fun onClickCard(position: Int) {
                Log.i(TAG, "Card Clicked in position : $position")
            }

        })
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getNumCol())

    }
}