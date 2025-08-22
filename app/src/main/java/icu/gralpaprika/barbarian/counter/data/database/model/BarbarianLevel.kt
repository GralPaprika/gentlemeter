package icu.gralpaprika.barbarian.counter.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barbarian_level")
data class BarbarianLevel(
    @PrimaryKey val id: Int = 1,
    val level: Int = 0
)
