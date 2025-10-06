package icu.gralpaprika.barbarian.counter.presentation.counter.screen

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import icu.gralpaprika.barbarian.counter.R
import icu.gralpaprika.barbarian.counter.presentation.counter.util.BarbarianImageUtil
import icu.gralpaprika.barbarian.counter.presentation.theme.BarbarianCounterTheme
import icu.gralpaprika.barbarian.counter.presentation.theme.PlusJakartaSans
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val WORD_ANIMATION_DELAY_MS = 600L
const val ENTER_ANIMATION_DURATION_MS = 2400
const val EXIT_ANIMATION_DURATION_MS = 2400
@Composable
fun CavemanScreen(
    onButtonClicked: () -> Unit = {},
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    visible: Boolean = false,
) {
    var isVisible by remember { mutableStateOf(visible) }
    var isExiting by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isExiting) {
        if (isExiting) {
            onDismissed()
            isExiting = false
        }
    }

    LaunchedEffect(Unit) {
        isVisible = true
        onShown()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(ENTER_ANIMATION_DURATION_MS)),
        exit = fadeOut(animationSpec = tween(EXIT_ANIMATION_DURATION_MS)) + slideOutVertically(
            targetOffsetY = { -it }, animationSpec = tween(EXIT_ANIMATION_DURATION_MS)
        ),
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
                // Dismiss button
                Button(onClick = {
                    isVisible = false
                    isExiting = true
                    scope.launch {
                        delay(EXIT_ANIMATION_DURATION_MS.toLong())
                        onButtonClicked()
                    }
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
        CavemanScreen(
            onButtonClicked = {},
            onDismissed = {},
            onShown = {},
            visible = true,
        )
    }
}