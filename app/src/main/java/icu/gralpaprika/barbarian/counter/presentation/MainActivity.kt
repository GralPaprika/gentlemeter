package icu.gralpaprika.barbarian.counter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import icu.gralpaprika.barbarian.counter.presentation.navigation.AppNavGraph
import icu.gralpaprika.barbarian.counter.presentation.navigation.Destination
import icu.gralpaprika.barbarian.counter.presentation.signin.viewmodel.SignInViewModel
import icu.gralpaprika.barbarian.counter.presentation.theme.BarbarianCounterTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarbarianCounterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val signInViewModel: SignInViewModel = hiltViewModel()
    val isSignedIn by signInViewModel.isSignedIn.collectAsState()
    val startDestination = if (isSignedIn) Destination.Counter else Destination.SignIn
//    val startDestination = Destination.Counter // For easier testing during development
    AppNavGraph(navController = navController, startDestination = startDestination)
}
