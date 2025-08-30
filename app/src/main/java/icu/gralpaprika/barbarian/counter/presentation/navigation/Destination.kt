package icu.gralpaprika.barbarian.counter.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    object Counter : Destination()

    @Serializable
    object SignIn : Destination()
}