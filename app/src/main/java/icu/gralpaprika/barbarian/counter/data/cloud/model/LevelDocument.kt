package icu.gralpaprika.barbarian.counter.data.cloud.model

import kotlinx.serialization.Serializable

@Serializable
data class LevelDocument(
    val id: String = "",
    val level: Int = 0,
    val lastUpdated: Long = 0L,
)
