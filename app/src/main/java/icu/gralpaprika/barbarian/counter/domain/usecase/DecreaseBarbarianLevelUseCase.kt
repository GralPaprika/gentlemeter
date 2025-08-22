package icu.gralpaprika.barbarian.counter.domain.usecase

import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import javax.inject.Inject

class DecreaseBarbarianLevelUseCase @Inject constructor(
    private val repository: BarbarianRepository
) {
    suspend operator fun invoke() {
        if (repository.getCurrentBarbarianLevel() > repository.minBarbarianLevel) {
            repository.decreaseBarbarianLevel()
        }
    }
}
