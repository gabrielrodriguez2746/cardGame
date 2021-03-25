package com.codechallenge.game

infix fun <T> List<T>.listEqual(toCompare: List<T>): Boolean {
    return when {
        this.size != toCompare.size -> false
        else -> {
            val pairList = zip(toCompare)
            return pairList.all { (suit1, suit2) -> suit1 == suit2 }
        }
    }
}
