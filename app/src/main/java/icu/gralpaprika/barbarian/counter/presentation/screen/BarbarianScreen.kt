package icu.gralpaprika.barbarian.counter.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import icu.gralpaprika.barbarian.counter.R
import icu.gralpaprika.barbarian.counter.presentation.screen.BarbarianState
import icu.gralpaprika.barbarian.counter.presentation.util.BarbarianImageUtil
import icu.gralpaprika.barbarian.counter.presentation.viewmodel.BarbarianViewModel

@Composable
fun BarbarianScreen(
    viewModel: BarbarianViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    BarbarianScreenContent(
        barbarianState = uiState,
        onGentlemanButtonClicked = { viewModel.onGentlemanButtonClicked() },
        onBarbarianButtonClicked = { viewModel.onBarbarianButtonClicked() }
    )
}

@Composable
fun BarbarianScreenContent(
    barbarianState: BarbarianState,
    onGentlemanButtonClicked: () -> Unit,
    onBarbarianButtonClicked: () -> Unit
) {
    val screenSize = LocalWindowInfo.current.containerSize
    val screenHeight = screenSize.height
    val screenWidth = screenSize.width
    var showLevelUp by remember { mutableStateOf(false) }
    val imageSize = when {
        screenHeight < 600 -> 200.dp
        screenHeight < 800 -> 250.dp
        else -> 300.dp
    }
    // Show overlay when reaching level 10
    LaunchedEffect(barbarianState.barbarianLevel) {
        if (barbarianState.barbarianLevel == MAX_BARBARIAN_LEVEL) {
            showLevelUp = true
        }
    }
    val padding = when {
        screenWidth < 400 -> 12.dp
        else -> 16.dp
    }
    val spacing = when {
        screenHeight < 600 -> 16.dp
        else -> 24.dp
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (barbarianState.barbarianLevel < MAX_BARBARIAN_LEVEL) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(
                        R.string.barbarian_acts_counter,
                        barbarianState.barbarianLevel
                    ),
                    fontSize = when {
                        screenWidth < 400 -> 14.sp
                        else -> 16.sp
                    },
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Card(
                    modifier = Modifier
                        .size(imageSize)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                ) {
                    Image(
                        painter = painterResource(
                            id = BarbarianImageUtil.getImageForBarbarianLevel(barbarianState.barbarianLevel)
                        ),
                        contentDescription = stringResource(R.string.gentleman_image_description),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                // Buttons
                if (barbarianState.barbarianLevel == MIN_BARBARIAN_LEVEL) {
                    Button(
                        onClick = onBarbarianButtonClicked,
                        modifier = Modifier.fillMaxWidth(0.7f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.barbarian_button),
                            fontSize = when {
                                screenWidth < 400 -> 12.sp
                                else -> 14.sp
                            }
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onGentlemanButtonClicked,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.gentleman_button),
                                fontSize = when {
                                    screenWidth < 400 -> 12.sp
                                    else -> 14.sp
                                }
                            )
                        }
                        Button(
                            onClick = onBarbarianButtonClicked,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.barbarian_button),
                                fontSize = when {
                                    screenWidth < 400 -> 12.sp
                                    else -> 14.sp
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        LevelUpOverlay(
            visible = showLevelUp,
            imageSize = imageSize,
            onButtonClicked = { onBarbarianButtonClicked() },
            onDismissed = { showLevelUp = false },
        )
    }
}

private const val MIN_BARBARIAN_LEVEL = 0
private const val MAX_BARBARIAN_LEVEL = 10
