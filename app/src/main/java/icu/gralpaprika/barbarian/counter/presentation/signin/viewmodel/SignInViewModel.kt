package icu.gralpaprika.barbarian.counter.presentation.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import icu.gralpaprika.barbarian.counter.domain.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String) : SignInState()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<SignInState>(SignInState.Idle)
    val state: StateFlow<SignInState> = _state

    fun signIn(email: String, password: String) {
        _state.value = SignInState.Loading
        viewModelScope.launch {
            val result = signInUseCase(email, password)
            _state.value = when {
                result.isSuccess -> SignInState.Success
                else -> SignInState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}
