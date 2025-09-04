package icu.gralpaprika.barbarian.counter.domain.repository

interface BarbarianRepository {
    suspend fun increaseBarbarianLevel()
    suspend fun decreaseBarbarianLevel()
    suspend fun increaseAndResetBarbarianLevel()
    suspend fun getCurrentBarbarianLevel(): Int
    suspend fun sync()
}
