package icu.gralpaprika.barbarian.counter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianAct
import kotlinx.coroutines.flow.Flow

@Dao
interface BarbarianActDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(act: BarbarianAct)

    @Query("SELECT * FROM barbarian_acts WHERE synced = 0")
    suspend fun getAllNotSynced(): List<BarbarianAct>

    @Query("UPDATE barbarian_acts SET synced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: String)
}
