package icu.gralpaprika.barbarian.counter.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import icu.gralpaprika.barbarian.counter.R
import icu.gralpaprika.barbarian.counter.presentation.screen.model.OvalShapeSize
import icu.gralpaprika.barbarian.counter.presentation.shapes.OvalCornerShape
import icu.gralpaprika.barbarian.counter.presentation.theme.BarbarianCounterTheme
import icu.gralpaprika.barbarian.counter.presentation.theme.PlayfairDisplay
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
    var showModal by remember { mutableStateOf(false) }

    val ovalBoxSize = when {
        screenHeight < 600 -> OvalShapeSize(width = 200.dp, height = 300.dp)
        screenHeight < 800 -> OvalShapeSize(width = 250.dp, height = 350.dp)
        else -> OvalShapeSize(width = 300.dp, height = 400.dp)
    }

    LaunchedEffect(barbarianState.barbarianLevel) {
        if (barbarianState.barbarianLevel == MAX_BARBARIAN_LEVEL) {
            showLevelUp = true
        }
    }

    val padding = when {
        screenWidth < 400 -> 12.dp
        else -> 16.dp
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (barbarianState.barbarianLevel > MIN_BARBARIAN_LEVEL) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
            ) {
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

        if (barbarianState.barbarianLevel < MAX_BARBARIAN_LEVEL) {
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
                        barbarianState.barbarianLevel
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
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .size(width = ovalBoxSize.width, height = ovalBoxSize.height)
                        .clip(OvalCornerShape())
                        .background(MaterialTheme.colorScheme.onPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(
                            id = BarbarianImageUtil.getImageForBarbarianLevel(barbarianState.barbarianLevel)
                        ),
                        contentDescription = stringResource(R.string.gentleman_image_description),
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onBarbarianButtonClicked,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.barbarian_button),
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

private const val MIN_BARBARIAN_LEVEL = 0
private const val MAX_BARBARIAN_LEVEL = 10

@Preview(showBackground = true)
@Composable
fun BarbarianScreenPreview() {
    BarbarianCounterTheme(darkTheme = true, dynamicColor = true) {
        BarbarianScreenContent(
            barbarianState = BarbarianState(barbarianLevel = 0),
            onGentlemanButtonClicked = {},
            onBarbarianButtonClicked = {}
        )
    }
}
