package icu.gralpaprika.barbarian.counter.domain.model

sealed class SyncResult {
    object Success : SyncResult()
    data class Error(val message: String) : SyncResult()
}