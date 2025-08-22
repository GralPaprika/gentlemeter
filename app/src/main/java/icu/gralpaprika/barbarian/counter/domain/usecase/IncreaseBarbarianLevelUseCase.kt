package icu.gralpaprika.barbarian.counter.domain.usecase

import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import javax.inject.Inject

class IncreaseBarbarianLevelUseCase @Inject constructor(
    private val repository: BarbarianRepository
) {
    operator fun invoke() {
        if (repository.getCurrentBarbarianLevel() < repository.maxBarbarianLevel) {
            repository.increaseBarbarianLevel()
        } else {
            repository.resetBarbarianLevel()
        }
    }
}
