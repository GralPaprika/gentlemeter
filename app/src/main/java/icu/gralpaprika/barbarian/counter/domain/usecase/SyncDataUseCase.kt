package icu.gralpaprika.barbarian.counter.domain.usecase

import icu.gralpaprika.barbarian.counter.domain.model.SyncResult
import icu.gralpaprika.barbarian.counter.domain.repository.AuthRepository
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import javax.inject.Inject

class SyncDataUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val repository: BarbarianRepository
) {
    suspend operator fun invoke(): SyncResult {
        if (authRepository.isUserSignedIn()) {
            val remoteDate = repository.getLatestRemoteUpdateDate()
            val localDate = repository.getLatestLocalUpdateDate()
            return if (remoteDate == null || localDate > remoteDate) {
                repository.syncToCloud()
            } else if (localDate < remoteDate) {
                repository.syncFromCloud()
            } else {
                SyncResult.Success
            }
        }
        return SyncResult.Error(SyncResult.ErrorType.UserIsNotAuthenticated)
    }
}
