package icu.gralpaprika.barbarian.counter.presentation.counter.screen

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import icu.gralpaprika.barbarian.counter.BuildConfig
import icu.gralpaprika.barbarian.counter.R
import icu.gralpaprika.barbarian.counter.presentation.components.LoadingScreen
import icu.gralpaprika.barbarian.counter.presentation.counter.screen.model.CounterScreenState
import icu.gralpaprika.barbarian.counter.presentation.counter.screen.model.OvalShapeSize
import icu.gralpaprika.barbarian.counter.presentation.counter.util.BarbarianImageUtil
import icu.gralpaprika.barbarian.counter.presentation.counter.viewmodel.CounterViewModel
import icu.gralpaprika.barbarian.counter.presentation.shapes.OvalCornerShape
import icu.gralpaprika.barbarian.counter.presentation.theme.BarbarianCounterTheme
import icu.gralpaprika.barbarian.counter.presentation.theme.PlayfairDisplay
import icu.gralpaprika.barbarian.counter.presentation.theme.PlusJakartaSans

private const val minBarbarianLevel = BuildConfig.BARBARIAN_MIN_LEVEL
private const val maxBarbarianLevel = BuildConfig.BARBARIAN_MAX_LEVEL

@Composable
fun CounterScreen() {
    val viewModel: CounterViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is CounterScreenState.Content -> CounterScreenContent(
            barbarianLevel = (uiState as CounterScreenState.Content).barbarianLevel,
            onBarbarianButtonClicked = { viewModel.onBarbarianButtonClicked() },
            onGentlemanButtonClicked = { viewModel.onGentlemanButtonClicked() },
        )
        CounterScreenState.Loading -> LoadingScreen()
    }
}

@Composable
fun CounterScreenContent(
    barbarianLevel: Int,
    onBarbarianButtonClicked: () -> Unit,
    onGentlemanButtonClicked: () -> Unit,
) {
    val screenSize = LocalWindowInfo.current.containerSize
    val screenHeight = screenSize.height
    val screenWidth = screenSize.width

    var showLevelUp by remember { mutableStateOf(false) }
    var showModal by remember { mutableStateOf(false) }
    
    val ovalBoxSize = when {
        screenHeight < 600 -> OvalShapeSize(width = 200.dp, height = 300.dp)
        screenHeight < 800 -> OvalShapeSize(width = 250.dp, height = 350.dp)
        else -> OvalShapeSize(width = 300.dp, height = 400.dp)
    }

    LaunchedEffect(barbarianLevel) {
        if (barbarianLevel == maxBarbarianLevel) {
            showLevelUp = true
        }
    }

    val padding = when {
        screenWidth < 400 -> 12.dp
        else -> 16.dp
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd),
            horizontalAlignment = Alignment.End
        ) {
            if (barbarianLevel > minBarbarianLevel) {
                IconButton(
                    onClick = { showModal = true },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.top_hat),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }

        ConfirmDialog(
            showModal = showModal,
            onDismiss = { showModal = false },
            onConfirm = {
                onGentlemanButtonClicked()
                showModal = false
            },
        )

        if (barbarianLevel < maxBarbarianLevel) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        R.string.barbarian_acts_counter,
                        barbarianLevel
                    ),
                    fontSize = 55.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = PlayfairDisplay,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(R.string.ungentlemanly_acts),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PlusJakartaSans,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .size(width = ovalBoxSize.width, height = ovalBoxSize.height)
                        .clip(OvalCornerShape())
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(
                            id = BarbarianImageUtil.getImageForBarbarianLevel(barbarianLevel)
                        ),
                        contentDescription = stringResource(R.string.gentleman_image_description),
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { onBarbarianButtonClicked() },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.barbarian_button),
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        LevelUpOverlay(
            visible = showLevelUp,
            imageSize = ovalBoxSize.height,
            onButtonClicked = { onBarbarianButtonClicked() },
            onDismissed = { showLevelUp = false },
        )
    }
}

@Composable
fun ConfirmDialog(showModal: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (showModal) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            text = { Text(stringResource(R.string.gentleman_dialog_message)) },
            confirmButton = {
                Button(
                    onClick = { onConfirm() },
                ) {
                    Text(stringResource(R.string.gentleman_dialog_confirm))
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() },
                ) {
                    Text(stringResource(R.string.gentleman_dialog_cancel))
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CounterScreenContentPreview() {
    BarbarianCounterTheme(darkTheme = true) {
        CounterScreenContent(
            barbarianLevel = 3,
            onGentlemanButtonClicked = {},
            onBarbarianButtonClicked = {}
        )
    }
}
