package com.example.firstcomposeactivity.flow

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


@Composable
fun RewardItem(
    modifier: Modifier = Modifier, rewardAnimatable: Animatable<Float, AnimationVector1D>,
    updateSourceOffset: (Offset, IntSize) -> Unit
) {
    Box(
        modifier = Modifier.onGloballyPositioned {
            updateSourceOffset(it.positionInRoot(), it.size)
        },
        contentAlignment = Alignment.Center,
    ) {
        LinearProgressIndicator(
            modifier = modifier
                .rotate(-90f)
                .size(54.dp)
                .clip(RoundedCornerShape(16.dp)),
            color = Color.Green,
            progress = rewardAnimatable.value
        )

        Text(
            text = "${(rewardAnimatable.value * 100).roundToInt()}%",
            color = Color.White
        )
    }
}