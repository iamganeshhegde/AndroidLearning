package com.example.firstcomposeactivity.flow

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun MyHorizontalRow(
    updateDestinationOffset: (Offset, IntSize) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MyBorderBox(modifier = Modifier.width(220.dp))
        Box(modifier = Modifier
            .size(54.dp)
            .background(
                color = Color.Red, shape = RoundedCornerShape(12.dp)
            )
            .onGloballyPositioned {
                updateDestinationOffset(it.positionInRoot(), it.size)
            })
        MyBorderBox(modifier = Modifier)
    }
}