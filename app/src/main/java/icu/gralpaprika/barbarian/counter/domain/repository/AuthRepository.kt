package icu.gralpaprika.barbarian.counter.domain.repository

interface AuthRepository {
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
    suspend fun signUpWithEmail(email: String, password: String): Result<Unit>
    fun isUserSignedIn(): Boolean
}
