package com.rsmi.memory.models

enum class BoardSize (val numCards: Int){
    EASY(8),
    MEDIUM(18),
    HARD(24);

   /* companion object {
        fun getByValue(value: Int) = values().first {it.numCards == value}
    }*/

    fun getNumCol(): Int {
        return when (this) {
            EASY -> numCards/4
            else -> numCards/6
        }


    }

    fun getNumLines(): Int {
        return  numCards / getNumCol()
    }

    fun getNumPairs(): Int {
        return numCards / 2
    }


}