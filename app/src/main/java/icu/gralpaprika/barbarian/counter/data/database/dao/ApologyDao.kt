package icu.gralpaprika.barbarian.counter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import icu.gralpaprika.barbarian.counter.data.database.model.Apology
import kotlinx.coroutines.flow.Flow

@Dao
interface ApologyDao {
    @Insert
    suspend fun insert(apology: Apology)

    @Query("SELECT * FROM apology ORDER BY timestamp DESC")
    fun getAllApologies(): Flow<List<Apology>>

    @Query("SELECT COUNT(*) FROM apology")
    fun getApologyCount(): Flow<Int>
}
