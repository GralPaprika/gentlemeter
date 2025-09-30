package icu.gralpaprika.barbarian.counter.domain.model

sealed class SyncResult {
    object Success : SyncResult()
    data class Error(val type: ErrorType, val message: String? = null) : SyncResult()

    enum class ErrorType(val description: String) {
        UserIsNotAuthenticated("User is not authenticated"),
        SyncToCloudFailed("Sync to cloud failed"),
        SyncFromCloudFailed("Sync from cloud failed"),
    }
}