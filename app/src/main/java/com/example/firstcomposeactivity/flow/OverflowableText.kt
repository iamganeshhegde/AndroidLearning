package com.example.firstcomposeactivity.flow

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.MeasurePolicy

@Composable
fun OverflowableText() {


    fun overlappingRowMeasurePolicy(overlapFactor: Float) = MeasurePolicy { measurables, constraints ->
        val placeables = measurables.map { measurable -> measurable.measure(constraints)}
        val height = placeables.maxOf { it.height }
        val width = (placeables.subList(1,placeables.size).sumOf { it.width  }* overlapFactor + placeables[0].width).toInt()
        layout(width,height) {
            var xPos = 0
            for (placeable in placeables) {
                placeable.placeRelative(xPos, 0, 0f)
                xPos += (placeable.width * overlapFactor).toInt()
            }
        }
    }
}