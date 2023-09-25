package com.rsmi.memory

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.snackbar.Snackbar
import com.rsmi.memory.models.BoardSize
import com.rsmi.memory.models.MemoryGame
import java.util.Locale

class MainActivity : AppCompatActivity() {

	private lateinit var clRoot: ConstraintLayout
	private lateinit var rvBoard: RecyclerView
	private lateinit var tvMoves: TextView
	private lateinit var tvNumPairs: TextView

	private var boardSize: BoardSize = BoardSize.MEDIUM

	private lateinit var memoryGame: MemoryGame
	private lateinit var adapter: MemoryBoardAdapter

	companion object {
		const val TAG = "MainActivity"
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		clRoot = findViewById(R.id.clRoot)
		rvBoard = findViewById(R.id.rvBoard)
		tvMoves = findViewById(R.id.tvMoves)
		tvNumPairs = findViewById(R.id.tvPairs)

		initializeGame()

	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_main, menu)
		return true
	}

	private fun showAlertDialog(
		title: String,
		view: View?,
		positiveButtonClickListener: View.OnClickListener,
	) {
		AlertDialog.Builder(this).setTitle(title).setView(view)
			.setNegativeButton("CANCEL", null)
			.setPositiveButton("OK") { _, _ ->
				positiveButtonClickListener.onClick(null)
			}
			.show()
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.miRefresh -> {
				if (memoryGame.getNumMoves() > 0 && !memoryGame.haveWonGame()) {
					showAlertDialog("Quit your current game?", null, View.OnClickListener {
						initializeGame()

					})
				} else {
					//initializeGame()
				}
			}

			R.id.miLevels -> {
				val gameLevels = LayoutInflater.from(this).inflate(R.layout.dialog_game_level,null)
				val rgGameLevel = gameLevels.findViewById<RadioGroup>(R.id.rgGameLevels)

				Log.i(TAG, "rgGameLevel : $rgGameLevel")

				when(boardSize){
					BoardSize.EASY -> rgGameLevel.check(R.id.rbEasyLevel)
					BoardSize.MEDIUM -> rgGameLevel.check(R.id.rbMediumLevel)
					else -> rgGameLevel.check(R.id.rbHardLevel)
				}

				showAlertDialog("Choose a level", gameLevels, View.OnClickListener {
					boardSize = when(rgGameLevel.checkedRadioButtonId){
						R.id.rbEasyLevel -> BoardSize.EASY
						R.id.rbMediumLevel -> BoardSize.MEDIUM
						else -> {
							BoardSize.HARD
						}
					}
					initializeGame()
				})

			}
		}

		return super.onOptionsItemSelected(item)
	}

	private fun initializeGame() {
		tvNumPairs.text = "Pairs: 0/${boardSize.getNumPairs()}"
		tvNumPairs.setTextColor(ContextCompat.getColor(this, R.color.color_progress_none) as Int)
		tvMoves.text = "${
			boardSize.toString().lowercase().capitalize(Locale.ROOT)
		}: ${boardSize.getNumLines()} x ${boardSize.getNumCol()}"
		memoryGame = MemoryGame(boardSize)

		adapter = MemoryBoardAdapter(
			this,
			boardSize,
			memoryGame.cards,
			object : MemoryBoardAdapter.CardClickListener {
				override fun onClickCard(position: Int) {
					updateGameWithFlip(position)
				}

			})

		rvBoard.adapter = adapter
		rvBoard.setHasFixedSize(true)
		rvBoard.layoutManager = GridLayoutManager(this, boardSize.getNumCol())
	}

	private fun updateGameWithFlip(position: Int) {
		//Error Handling
		if (memoryGame.haveWonGame()) {
			Snackbar.make(clRoot, "You already won", Snackbar.LENGTH_LONG).show()
			return
		}

		if (memoryGame.isCardFaceUp(position)) {
			Snackbar.make(clRoot, "Invalid move", Snackbar.LENGTH_SHORT).show()

			return
		}

		if (memoryGame.flipCard(position)) {
			Log.i(TAG, "--> Number of pairs found: ${memoryGame.numPairsFound}")

			tvNumPairs.text = "Pairs: ${memoryGame.numPairsFound} / ${boardSize.getNumPairs()}"
			//Change the text color
			val color = ArgbEvaluator().evaluate(
				memoryGame.numPairsFound.toFloat() / boardSize.getNumPairs(),
				ContextCompat.getColor(this, R.color.color_progress_none),
				ContextCompat.getColor(this, R.color.color_progress_full)
			)

			tvNumPairs.setTextColor(color as Int)

		}



		tvMoves.text = "Moves: ${memoryGame.getNumMoves()}"

		if (memoryGame.haveWonGame()) Snackbar.make(
			clRoot,
			"Congratulations , you won !!",
			Snackbar.LENGTH_LONG
		).show()
		adapter.notifyDataSetChanged()
	}


}