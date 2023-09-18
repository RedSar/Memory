package com.rsmi.memory.models

import android.util.Log
import com.rsmi.memory.utils.DEFAULT_CARDS

class MemoryGame(private val boardSize: BoardSize) {

    private var positionOfSingleSelectedCard: Int? =
        null // storing the position of the card previously selected
    val cards: List<Card>

    private var numPairsFound = 0

    companion object {
        const val TAG = "MemoryGame"
    }

    init {

        val distinctCards = DEFAULT_CARDS.shuffled().take(boardSize.getNumPairs())
        val shuffledCards = (distinctCards + distinctCards).shuffled()
        cards = shuffledCards.map { Card(it) }

        Log.i(TAG, "init block positionOfSingleSelectedCard = $positionOfSingleSelectedCard")
    }

    fun flipCard(position: Int): Boolean {
        val card = cards[position]
        var matchFound = false
        Log.i(TAG, "flipCard block positionOfSingleSelectedCard = $positionOfSingleSelectedCard")

        if (positionOfSingleSelectedCard == null) {
            restoreCards()
            positionOfSingleSelectedCard = position
            Log.i(TAG, "flipCard -> 'if block' positionOfSingleSelectedCard = $positionOfSingleSelectedCard")

        } else {
            matchFound = checkForMatch(positionOfSingleSelectedCard!!, position)
            positionOfSingleSelectedCard = null
            Log.i(TAG, "flipCard -> 'else block' positionOfSingleSelectedCard = $positionOfSingleSelectedCard")
        }

        card.isFaceUp = !card.isFaceUp // Flip the selected Card

        return matchFound
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].id != cards[position2].id) {
            return false
        }
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound++
        Log.i(TAG, "checkForMatch -> 'Match Found' numPairsFound = $numPairsFound")
        return true
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) card.isFaceUp = false
        }
    }

    fun haveWonGame(): Boolean {
        return (numPairsFound == boardSize.getNumPairs())
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }
}
