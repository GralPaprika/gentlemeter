package icu.gralpaprika.barbarian.counter.presentation.counter.screen.model

import kotlinx.serialization.Serializable

sealed class CounterScreenState {
    @Serializable
    data class Counter(val barbarianLevel: Int) : CounterScreenState()

    @Serializable
    object CavemanScreen : CounterScreenState()

    @Serializable
    object Loading : CounterScreenState()
}