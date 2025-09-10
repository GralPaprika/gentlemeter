package icu.gralpaprika.barbarian.counter.domain.repository

import icu.gralpaprika.barbarian.counter.data.database.model.Apology
import kotlinx.coroutines.flow.Flow

interface ApologyRepository {
    suspend fun insertApology(apology: Apology)
    suspend fun getAllApologies(): List<Apology>
    suspend fun getApologyCount(): Int
}
