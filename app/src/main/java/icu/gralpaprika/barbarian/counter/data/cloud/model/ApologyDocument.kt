package icu.gralpaprika.barbarian.counter.data.cloud.model

import kotlinx.serialization.Serializable

@Serializable
data class ApologyDocument(
    val id: String = "",
    val comments: String = "",
    val timestamp: Long = 0L,
)