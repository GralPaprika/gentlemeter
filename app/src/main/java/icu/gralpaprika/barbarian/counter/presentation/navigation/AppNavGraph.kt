package icu.gralpaprika.barbarian.counter.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import icu.gralpaprika.barbarian.counter.presentation.counter.screen.CounterScreen
import icu.gralpaprika.barbarian.counter.presentation.signin.screen.SignInScreen
import kotlinx.serialization.Serializable

@Serializable
object Counter

@Serializable
object SignIn

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Counter
    ) {
        composable<Counter> {
            CounterScreen(
                onNavigateToSignIn = {
                    navController.navigate(route = SignIn)
                }
            )
        }
        composable<SignIn> {
            SignInScreen()
        }
    }
}
