package icu.gralpaprika.barbarian.counter.domain.usecase

import icu.gralpaprika.barbarian.counter.domain.model.SyncResult
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import jakarta.inject.Inject

class SyncToCloudUseCase @Inject constructor(
    private val repository: BarbarianRepository
) {
    suspend operator fun invoke(): SyncResult {
        return repository.syncToCloud()
    }
}