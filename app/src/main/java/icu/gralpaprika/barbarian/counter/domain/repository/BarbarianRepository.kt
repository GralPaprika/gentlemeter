package icu.gralpaprika.barbarian.counter.domain.repository

import icu.gralpaprika.barbarian.counter.presentation.screen.BarbarianState

interface BarbarianRepository {
    fun increaseBarbarianLevel()
    fun decreaseBarbarianLevel()
    fun resetBarbarianLevel()
    fun getCurrentBarbarianLevel(): Int
    val maxBarbarianLevel: Int
    val minBarbarianLevel: Int
}
