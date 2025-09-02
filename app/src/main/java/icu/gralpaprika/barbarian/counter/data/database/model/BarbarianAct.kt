package icu.gralpaprika.barbarian.counter.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "barbarian_acts")
data class BarbarianAct(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val type: String,
    val timestamp: Long = System.currentTimeMillis(),
    val comments: String = "",
    val synced: Boolean = false
)
