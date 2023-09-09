package com.example.firstcomposeactivity.flow

import android.graphics.BlurMaskFilter
import android.graphics.PathMeasure
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TranslatingObjects() {

    val infiniteTransition = rememberInfiniteTransition()

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Restart
        ),
        targetValue = 1f
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
                .fillMaxHeight(fraction = 0.5f)
                .background(Color.Green.copy(alpha = 0.2f))
                .shadow(
                    color = Color(android.graphics.Color.parseColor("#9146EB")),
                    borderRadius = 12.dp,
                    spread = 10.dp,
                    blurRadius = 16.dp,
                    paintingStyle = PaintingStyle.Stroke,
                    strokeWidth = 10f,
//                    shape = "Circular"
                )
        ) {
            // Start & End Points
//            val start = Offset(0f, size.height / 2)
            val start = Offset(0f, 0f)
//            val end = Offset(size.width, size.height / 2)
            val end = Offset(size.width, size.height)

            // Control Points
            val c1 = Offset(size.width / 4, 0f)
//            val c1 = Offset(size.width / 2, 0f)
            val c2 = Offset(3 * size.width / 4, size.height)
//            val c2 = Offset(size.width, size.height / 2)
            val c3 = Offset(3 * size.width / 4, 0f)
//            val c3 = Offset(size.width / 2, size.height)
            val c4 = Offset(size.width / 4, size.height)
//            val c4 = Offset(0f, size.height / 2)

            val infinityPath = Path().apply {

                // Move to Start Position
                moveTo(start.x, start.y)

                // first curve : start -> c1 -> c2 -> end
                cubicTo(
                    c1.x, c1.y,
                    c2.x, c2.y,
                    end.x, end.y
                )

                // Second curve : end -> c3 -> c4 -> start
                cubicTo(
                    c3.x, c3.y,
                    c4.x, c4.y,
                    start.x, start.y
                )


                // Close the Path
                close()
            }



            drawPath(
                path = infinityPath,
                color = Color.Red,
                style = Stroke(width = 1.dp.toPx()),
            )


            val pos = FloatArray(2)
            val tan = FloatArray(2)

            PathMeasure().apply {
                setPath(infinityPath.asAndroidPath(), false)
                getPosTan(length * progress, pos, tan)
            }

            drawCircle(
                radius = 10.dp.toPx(),
                color = Color.Blue,
                center = Offset(pos[0], pos[1])
            )
        }
    }
}



fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier,
    paintingStyle: PaintingStyle = PaintingStyle.Fill,
    strokeWidth: Float = 0f,
    shape: String = "Rectangle"
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            paint.style = paintingStyle
            paint.strokeWidth = strokeWidth

            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            when(shape) {
                "Rectangle" -> {
                    val spreadPixel = spread.toPx()
                    val leftPixel = (0f - spreadPixel)
                    val topPixel = (0f - spreadPixel)
                    val rightPixel = (this.size.width + spreadPixel)
                    val bottomPixel = (this.size.height + spreadPixel)

                    it.drawRoundRect(
                        left = leftPixel,
                        top = topPixel,
                        right = rightPixel,
                        bottom = bottomPixel,
                        radiusX = borderRadius.toPx(),
                        radiusY = borderRadius.toPx(),
                        paint
                    )
                }
                "Circular" -> {
                    it.drawCircle(
                        radius = this.size.width/2 + spread.toPx(),
                        center = Offset(this.size.width/2, this.size.width/2),
                        paint = paint
                    )
                }
            }
        }
    }
)