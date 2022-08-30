package com.example.firstcomposeactivity

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import kotlin.math.abs

fun Path.standardQuadFromTo(offsetfrom: Offset, to: Offset) {

    quadraticBezierTo(
        offsetfrom.x,
        offsetfrom.y,
        abs(offsetfrom.x + to.x) / 2f,
        abs(offsetfrom.y + to.y) / 2f
    )
}