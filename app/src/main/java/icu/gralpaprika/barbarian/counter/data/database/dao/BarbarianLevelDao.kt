package icu.gralpaprika.barbarian.counter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface BarbarianLevelDao {
    @Query("SELECT * FROM barbarian_level LIMIT 1")
    suspend fun getBarbarianLevel(): BarbarianLevel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBarbarianLevel(level: BarbarianLevel)
}
