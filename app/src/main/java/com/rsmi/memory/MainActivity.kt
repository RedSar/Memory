package com.rsmi.memory

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rsmi.memory.models.BoardSize
import com.rsmi.memory.models.MemoryGame

class MainActivity : AppCompatActivity() {

    private lateinit var clRoot : ConstraintLayout
    private lateinit var rvBoard : RecyclerView
    private lateinit var tvMoves : TextView
    private lateinit var tvNumPairs : TextView

    private  var boardSize : BoardSize = BoardSize.MEDIUM

    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter : MemoryBoardAdapter

    companion object{
        const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clRoot = findViewById(R.id.clRoot)
        rvBoard = findViewById(R.id.rvBoard)
        tvMoves = findViewById(R.id.tvMoves)
        tvNumPairs = findViewById(R.id.tvPairs)

        tvNumPairs.text = "Pairs: 0/${boardSize.getNumPairs()}"
        tvNumPairs.setTextColor(ContextCompat.getColor(this,R.color.color_progress_none) as Int)

        memoryGame = MemoryGame(boardSize)

        adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object : MemoryBoardAdapter.CardClickListener{
            override fun onClickCard(position: Int) {
                updateGameWithFlip(position)
            }

        })

        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getNumCol())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun updateGameWithFlip(position: Int) {
        //Error Handling
        if (memoryGame.haveWonGame()) {
            Snackbar.make(clRoot,"You already won" , Snackbar.LENGTH_LONG).show()
            return
        }

        if (memoryGame.isCardFaceUp(position)) {
            Snackbar.make(clRoot,"Invalid move" , Snackbar.LENGTH_SHORT).show()

            return
        }

        if (memoryGame.flipCard(position)) {
            Log.i(TAG, "--> Number of pairs found: ${memoryGame.numPairsFound}")

            tvNumPairs.text = "Pairs: ${memoryGame.numPairsFound} / ${boardSize.getNumPairs()}"
            //Change the text color
            val color = ArgbEvaluator().evaluate(memoryGame.numPairsFound.toFloat() / boardSize.getNumPairs(),
                ContextCompat.getColor(this, R.color.color_progress_none),
                ContextCompat.getColor(this, R.color.color_progress_full)
            )

            tvNumPairs.setTextColor(color as Int)

        }



        tvMoves.text = "Moves: ${memoryGame.getNumMoves()}"

        if (memoryGame.haveWonGame()) Snackbar.make(clRoot, "Congratulations , you won !!",Snackbar.LENGTH_LONG).show()
        adapter.notifyDataSetChanged()
    }


}