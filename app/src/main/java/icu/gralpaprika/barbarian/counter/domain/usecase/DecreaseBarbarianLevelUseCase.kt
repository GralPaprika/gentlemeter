package icu.gralpaprika.barbarian.counter.domain.usecase

import icu.gralpaprika.barbarian.counter.BuildConfig
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import javax.inject.Inject

class DecreaseBarbarianLevelUseCase @Inject constructor(
    private val repository: BarbarianRepository
) {
    private val minBarbarianLevel = BuildConfig.BARBARIAN_MIN_LEVEL

    suspend operator fun invoke() {
        if (repository.getCurrentBarbarianLevel() > minBarbarianLevel) {
            repository.decreaseBarbarianLevel()
        }
    }
}
