package icu.gralpaprika.barbarian.counter.presentation.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import icu.gralpaprika.barbarian.counter.domain.usecase.SignInUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.IsUserSignedInUseCase
import icu.gralpaprika.barbarian.counter.presentation.signin.model.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val isUserSignedInUseCase: IsUserSignedInUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(isUserSignedIn())
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

    private fun isUserSignedIn() =
        if (isUserSignedInUseCase.execute()) {
            SignInState.Success
        } else {
            SignInState.Idle
        }
}
