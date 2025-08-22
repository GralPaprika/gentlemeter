package icu.gralpaprika.barbarian.counter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import icu.gralpaprika.barbarian.counter.data.database.model.Apology

@Dao
interface ApologyDao {
    @Insert
    suspend fun insert(apology: Apology)
}
