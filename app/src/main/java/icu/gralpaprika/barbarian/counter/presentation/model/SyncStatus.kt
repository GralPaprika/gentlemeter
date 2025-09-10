package icu.gralpaprika.barbarian.counter.presentation.model

sealed class SyncStatus {
    object Idle : SyncStatus()
    object Success : SyncStatus()
    data class Error(val message: String) : SyncStatus()
}