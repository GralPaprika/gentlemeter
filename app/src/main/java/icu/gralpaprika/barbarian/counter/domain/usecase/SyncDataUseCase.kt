package icu.gralpaprika.barbarian.counter.domain.usecase

import icu.gralpaprika.barbarian.counter.domain.model.SyncResult
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import javax.inject.Inject

class SyncDataUseCase @Inject constructor(
    private val repository: BarbarianRepository
) {
    suspend operator fun invoke(): SyncResult {
        return repository.syncData()
    }
}
