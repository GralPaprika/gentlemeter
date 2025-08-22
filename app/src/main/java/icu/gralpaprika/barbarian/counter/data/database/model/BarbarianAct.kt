package icu.gralpaprika.barbarian.counter.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barbarian_acts")
data class BarbarianAct(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val timestamp: Long = System.currentTimeMillis(),
    val comments: String = ""
)
