package icu.gralpaprika.barbarian.counter.domain.usecase

import icu.gralpaprika.barbarian.counter.presentation.screen.BarbarianState
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import javax.inject.Inject

class GetBarbarianLevelUseCase @Inject constructor(
    private val repository: BarbarianRepository
) {
    operator fun invoke(): Int = repository.getCurrentBarbarianLevel()
}
