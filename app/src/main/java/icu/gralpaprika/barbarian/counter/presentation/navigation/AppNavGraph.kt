package icu.gralpaprika.barbarian.counter.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import icu.gralpaprika.barbarian.counter.presentation.counter.screen.CounterScreen
import icu.gralpaprika.barbarian.counter.presentation.signin.screen.SignInScreen

@Composable
fun AppNavGraph(navController: NavHostController, startDestination: Destination) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Destination.Counter> {
            CounterScreen()
        }
        composable<Destination.SignIn> {
            SignInScreen(
                onSignInSuccess = {
                    navController.navigate(route = Destination.Counter) {
                        popUpTo(Destination.Counter)
                    }
                }
            )
        }
    }
}
