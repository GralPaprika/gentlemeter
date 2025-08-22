package icu.gralpaprika.barbarian.counter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianAct

@Dao
interface BarbarianActDao {
    @Insert
    suspend fun insert(act: BarbarianAct)
}
