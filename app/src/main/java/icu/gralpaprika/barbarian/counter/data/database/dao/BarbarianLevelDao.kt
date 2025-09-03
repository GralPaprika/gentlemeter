package icu.gralpaprika.barbarian.counter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface BarbarianLevelDao {
    @Query("SELECT * FROM barbarian_level")
    fun getBarbarianLevel(): Flow<BarbarianLevel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBarbarianLevel(level: BarbarianLevel)

    @Query("UPDATE barbarian_level SET level = level + 1 WHERE id = :id")
    suspend fun incrementLevel(id: String)

    @Query("UPDATE barbarian_level SET level = max(0, level - 1) WHERE id = :id")
    suspend fun decrementLevel(id: String)

    @Query("UPDATE barbarian_level SET level = 0 WHERE id = :id")
    suspend fun resetLevel(id: String)
}
