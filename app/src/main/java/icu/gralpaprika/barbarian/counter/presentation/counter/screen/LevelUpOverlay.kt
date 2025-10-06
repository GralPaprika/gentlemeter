package icu.gralpaprika.barbarian.counter.presentation.counter.screen

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import icu.gralpaprika.barbarian.counter.R
import icu.gralpaprika.barbarian.counter.presentation.counter.util.BarbarianImageUtil
import icu.gralpaprika.barbarian.counter.presentation.counter.viewmodel.CounterViewModel
import icu.gralpaprika.barbarian.counter.presentation.theme.BarbarianCounterTheme
import icu.gralpaprika.barbarian.counter.presentation.theme.PlusJakartaSans
import kotlinx.coroutines.delay

const val WORD_ANIMATION_DELAY_MS = 600L
const val ENTER_ANIMATION_DURATION_MS = 1400
const val FADE_OUT_DURATION_MS = 600
const val SLIDE_OUT_DURATION_MS = 1400
@Composable
fun LevelUpOverlay(
    onButtonClicked: () -> Unit = {},
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
) {
    var isExiting by remember { mutableStateOf(false) }

    LaunchedEffect(isExiting) {
        if (isExiting) {
            delay((FADE_OUT_DURATION_MS + SLIDE_OUT_DURATION_MS).toLong())
            onDismissed()
            isExiting = false
        }
    }

    LaunchedEffect(Unit) {
        onShown()
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(ENTER_ANIMATION_DURATION_MS)),
        exit = fadeOut(animationSpec = tween(FADE_OUT_DURATION_MS)) + slideOutVertically(
            targetOffsetY = { -it }, animationSpec = tween(SLIDE_OUT_DURATION_MS)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val titleString = stringResource(R.string.barbarian_overlay_title)

                val breakIterator = remember(titleString) {
                    titleString.split(" ").iterator()
                }

                var title by remember { mutableStateOf("") }

                LaunchedEffect(titleString) {
                    while (breakIterator.hasNext()) {
                        title = title + " " + breakIterator.next()
                        delay(WORD_ANIMATION_DELAY_MS)
                    }
                }

                Text(
                    text = title,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PlusJakartaSans,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Image(
                    painter = painterResource(id = BarbarianImageUtil.getImageForBarbarianLevel(10)),
                    contentDescription = stringResource(R.string.gentleman_image_description),
                    modifier = Modifier
                        .size(400.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    isExiting = true
                    onButtonClicked()
                }) {
                    Text(text = stringResource(R.string.dismiss))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LevelUpOverlayPreview() {
    BarbarianCounterTheme(darkTheme = true) {
        LevelUpOverlay(
            onButtonClicked = {},
            onDismissed = {},
            onShown = {}
        )
    }
}