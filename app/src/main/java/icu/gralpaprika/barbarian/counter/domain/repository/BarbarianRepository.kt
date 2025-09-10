package icu.gralpaprika.barbarian.counter.domain.repository

import icu.gralpaprika.barbarian.counter.domain.model.SyncResult

interface BarbarianRepository {
    suspend fun increaseBarbarianLevel()
    suspend fun decreaseBarbarianLevel()
    suspend fun increaseAndResetBarbarianLevel()
    suspend fun getCurrentBarbarianLevel(): Int
    suspend fun syncToCloud(): SyncResult
    suspend fun syncFromCloud(): SyncResult
}
