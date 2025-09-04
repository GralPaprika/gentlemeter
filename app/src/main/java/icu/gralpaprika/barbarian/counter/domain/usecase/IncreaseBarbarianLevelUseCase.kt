package icu.gralpaprika.barbarian.counter.domain.usecase

import icu.gralpaprika.barbarian.counter.BuildConfig
import icu.gralpaprika.barbarian.counter.data.database.model.Apology
import icu.gralpaprika.barbarian.counter.domain.repository.ApologyRepository
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import javax.inject.Inject

class IncreaseBarbarianLevelUseCase @Inject constructor(
    private val barbarianRepository: BarbarianRepository,
    private val apologyRepository: ApologyRepository,
) {
    private val maxBarbarianLevel = BuildConfig.BARBARIAN_MAX_LEVEL

    suspend operator fun invoke() {
        if (barbarianRepository.getCurrentBarbarianLevel() < maxBarbarianLevel) {
            barbarianRepository.increaseBarbarianLevel()
        } else {
            apologyRepository.insertApology(Apology(
                comments = "You have reached the maximum level of ${maxBarbarianLevel}.",
                timestamp = System.currentTimeMillis()
            ))
            barbarianRepository.increaseAndResetBarbarianLevel()
        }
    }
}
