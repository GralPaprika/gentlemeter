package icu.gralpaprika.barbarian.counter.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import icu.gralpaprika.barbarian.counter.R
import icu.gralpaprika.barbarian.counter.presentation.util.BarbarianImageUtil
import kotlinx.coroutines.delay

@Composable
fun LevelUpOverlay(
    visible: Boolean,
    imageSize: Dp,
    onButtonClicked: () -> Unit,
    onDismissed: () -> Unit,
) {
    var isExiting by remember { mutableStateOf(false) }

    // When exit starts, call onDismissed after animation
    LaunchedEffect(isExiting) {
        if (isExiting) {
            delay(1000) // match exit animation duration
            onDismissed()
        }
    }

    AnimatedVisibility(
        visible = visible && !isExiting,
        enter = fadeIn(animationSpec = tween(700)),
        exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
            targetOffsetY = { -it }, animationSpec = tween(1000)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background), // Solid background
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = BarbarianImageUtil.getImageForBarbarianLevel(10)),
                    contentDescription = stringResource(R.string.gentleman_image_description),
                    modifier = Modifier
                        .size(imageSize)
                        .padding(8.dp)
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
