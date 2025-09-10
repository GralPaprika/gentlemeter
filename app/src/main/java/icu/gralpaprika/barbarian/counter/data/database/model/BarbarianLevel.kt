package icu.gralpaprika.barbarian.counter.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "barbarian_level")
data class BarbarianLevel(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val level: Int = 0,
    val synced: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis(),
)
