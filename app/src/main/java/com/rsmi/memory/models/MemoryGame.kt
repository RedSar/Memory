package com.rsmi.memory.models

import com.rsmi.memory.utils.DEFAULT_CARDS

class MemoryGame(private val boardSize: BoardSize)  {
    val cards: List<Card>
    val numPairsFound = 0
    init {

        val distinctCards = DEFAULT_CARDS.shuffled().take(boardSize.getNumPairs())

        val shuffledCards = (distinctCards+distinctCards).shuffled()

        cards = shuffledCards.map { Card(it) }
    }

}