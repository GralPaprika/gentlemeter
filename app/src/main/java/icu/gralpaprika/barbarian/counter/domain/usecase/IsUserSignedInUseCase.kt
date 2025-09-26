package icu.gralpaprika.barbarian.counter.domain.usecase

import icu.gralpaprika.barbarian.counter.domain.repository.AuthRepository
import javax.inject.Inject

class IsUserSignedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean = authRepository.isUserSignedIn()
}

