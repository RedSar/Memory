package com.rsmi.memory.models

data class Card(
    val id: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false // did the user find the match card
)
