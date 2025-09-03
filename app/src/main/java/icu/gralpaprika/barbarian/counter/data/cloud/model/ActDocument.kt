package icu.gralpaprika.barbarian.counter.data.cloud.model

import kotlinx.serialization.Serializable

@Serializable
data class ActDocument(
    val id: String = "",
    val type: String = "",
    val comments: String = "",
    val timestamp: Long = 0L,
)
