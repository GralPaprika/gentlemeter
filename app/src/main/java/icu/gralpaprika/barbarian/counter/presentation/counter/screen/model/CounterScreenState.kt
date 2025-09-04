package icu.gralpaprika.barbarian.counter.presentation.counter.screen.model

sealed class CounterScreenState {
    data class Content(val barbarianLevel: Int) : CounterScreenState()
    object Loading : CounterScreenState()
    data class Error(val message: String) : CounterScreenState()
}