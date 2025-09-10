package icu.gralpaprika.barbarian.counter.presentation.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import icu.gralpaprika.barbarian.counter.R
import icu.gralpaprika.barbarian.counter.presentation.navigation.AppNavGraph
import icu.gralpaprika.barbarian.counter.presentation.navigation.Destination
import icu.gralpaprika.barbarian.counter.presentation.signin.model.SignInState
import icu.gralpaprika.barbarian.counter.presentation.signin.viewmodel.SignInViewModel
import icu.gralpaprika.barbarian.counter.presentation.theme.BarbarianCounterTheme
import icu.gralpaprika.barbarian.counter.presentation.activity.viewmodel.SharedViewModel
import icu.gralpaprika.barbarian.counter.presentation.model.SyncStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

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

    override fun onResume() {
        super.onResume()
        sharedViewModel.syncToCloud()
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val signInViewModel: SignInViewModel = hiltViewModel()
    val signInState by signInViewModel.state.collectAsState()
    val startDestination = if (signInState == SignInState.Success) Destination.Counter else Destination.SignIn
//    val startDestination = Destination.Counter // For easier testing during development
    AppNavGraph(navController = navController, startDestination = startDestination)
}
