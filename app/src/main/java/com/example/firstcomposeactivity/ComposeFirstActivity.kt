package com.example.firstcomposeactivity

import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants
import java.util.*
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ComposeFirstActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            /*Column(
                modifier = Modifier.background(color = Color.Green).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text("Hello")
                Text("World")
                Text("World")
            }*/

            /*Row(
                modifier = Modifier.background(color = Color.Green).width(200.dp).fillMaxHeight(0.75f)*//*.fillMaxSize(0.75f)*//*,
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Hello")
                Text("World")
                Text("World")
            }*/


            /*Column(modifier = Modifier
                .background(Color.Green)
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
                .border(width = 5.dp,color = Color.Magenta)
                .padding(5.dp)
                .border(width = 5.dp, color = Color.Blue)
                .padding(10.dp)
                .border(10.dp, Color.Cyan)
                .padding(10.dp)
                *//*.width(600.dp).requiredWidth(600.dp)*//*) {

                Text(text = "Hello", modifier = Modifier.clickable {

                })
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "World")

            }*/

            //card view
            /*val painter = painterResource(id = R.drawable.aaa)
            Box(modifier = Modifier.fillMaxWidth(0.5f).padding(16.dp)) {
                ImageCard(painter,"Nothing screenshot", "Have a look at the sceenshot")

            }*/


            /*val color = remember {
                mutableStateOf(Color.Yellow)
            }

            Column(modifier = Modifier.fillMaxSize()) {
                ColourBox(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    color.value = it
                }

                Box(modifier = Modifier
                    .weight(1f)
                    .background(color.value)
                    .fillMaxSize())
            }*/


//            SnackBarAndText()

//            LazyColums()


//            COnstraintLayoutCheck()

//            MyComposable()

//            SimpleAnimation()

//            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
//                CircularLoader(percentage = 0.8f, number = 100)
//            }


            /*Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color(0xFF101010)
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(1.dp, Color.Green, RoundedCornerShape(10.dp))
                        .padding(30.dp)
                ) {

                    var volume by remember {
                        mutableStateOf(0f)
                    }

                    var barcount = 10

                    MusicKnob(modifier = Modifier.size(100.dp)) {
                        volume = it
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Volumebar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        activeBars = (barcount * volume).toInt(),
                        barCOunt = barcount,
                    )
                }

            }*/


//            HomeScreen()


            /* androidx.compose.material.Surface(
                 color = Color(0xFF101010),
                 modifier = Modifier.fillMaxSize()
             ) {
                 Box(
                     modifier = Modifier.fillMaxSize(),
                     contentAlignment = Alignment.Center
                 ) {

                     Timer(
                         totalTime = 100L * 1000L,
                         handleColor = Color.Green,
                         inActiveBarColor = Color.DarkGray,
                         activeBarColor = Color(0xFF37b900),
                         modifier = Modifier.size(200.dp)
                     )
                 }
             }
             Timer()*/


            androidx.compose.material.Surface(color = Color(0xFF202020), modifier = Modifier.fillMaxSize()) {
                Navigation()
            }


        }
    }


}


@Composable
fun Navigation() {
    var navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)

        }
        composable("main_screen") {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Main Screen")
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true) {

        scale.animateTo(5f,
            animationSpec = tween(
                durationMillis = 500, easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            ))
        delay(3000L)


        navController.navigate("main_screen")

    }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().scale(scale.value)
    ) {

        Image(
            painterResource(id = R.drawable.ic_baseline_bubble_chart_24), contentDescription = "Image"
        )
    }
}


@Composable
fun Timer(
    totalTime: Long,
    handleColor: Color,
    inActiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValu: Float = 1f,
    strokeWidth: Dp = 5.dp
) {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    var value by remember {
        mutableStateOf(initialValu)
    }

    var currentTime by remember {
        mutableStateOf(totalTime)
    }

    var isTimeRunning by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = currentTime, key2 = isTimeRunning) {

        if (currentTime > 0 && isTimeRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }

    Box(
        modifier = Modifier.onSizeChanged {
            size = it
        }, contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = modifier) {
            drawArc(
                color = inActiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false, // check this
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )


            drawArc(
                color = activeBarColor,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false, // check this
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            val center = Offset(size.width / 2f, size.height / 2f)

            val beta = (250f * value + 145f) * (PI / 180f).toFloat()

            val r = size.width / 2f
            val a = cos(beta) * r

            var b = sin(beta) * r

            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }

        Text(
            text = (currentTime / 1000L).toString(),
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Button(
            onClick = {
                if (currentTime <= 0L) {
                    currentTime = totalTime
                    isTimeRunning = true
                } else {

                    isTimeRunning = !isTimeRunning
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (!isTimeRunning || currentTime <= 0L) {
                    Color.Green
                } else {
                    Color.Red
                }
            )
        ) {
            Text(
                text = if (isTimeRunning && currentTime >= 0) "Stop"
                else if (!isTimeRunning && currentTime >= 0) "Start"
                else "Restart"
            )

        }

    }


}


@Composable
fun Volumebar(
    modifier: Modifier = Modifier,
    activeBars: Int = 0,
    barCOunt: Int = 10
) {

    BoxWithConstraints(
        contentAlignment = Alignment.Center, modifier = modifier
    ) {

        val barWidth = remember {
            constraints.maxWidth / (2f * barCOunt)
        }

        Canvas(modifier = modifier) {

            for (i in 0 until barCOunt) {
                drawRoundRect(
                    color = if (i in 0..activeBars) Color.Green else Color.Gray,
                    topLeft = Offset(x = i * barWidth * 2f + barWidth / 2f, 0f),
                    size = Size(
                        barWidth, constraints.maxHeight.toFloat()
                    ),
                    cornerRadius = CornerRadius(0f)
                )
            }
        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    onValueChanged: (Float) -> Unit,
) {
    var rotation by remember {
        mutableStateOf(limitingAngle)
    }

    var touchX by remember {
        mutableStateOf(0f)
    }

    var touchy by remember {
        mutableStateOf(0f)
    }

    var centerX by remember {
        mutableStateOf(0f)
    }

    var centerY by remember {
        mutableStateOf(0f)
    }


    Image(
        painter = painterResource(id = R.drawable.music_knob),
        contentDescription = "Music knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val windowBounds = it.boundsInWindow()
                centerX = windowBounds.size.width / 2f
                centerY = windowBounds.size.height / 2f
            }
            .pointerInteropFilter { event ->
                touchX = event.x
                touchy = event.y

                val angle = -atan2(centerX - touchX, centerY - touchy) * (108f / PI).toFloat()

                when (event.action) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE -> {
                        if (angle !in -limitingAngle..limitingAngle) {

                            val fixedAngle = if (angle in -180f..-limitingAngle) {
                                360 + angle
                            } else {
                                angle
                            }

                            rotation = fixedAngle

                            val percent = (fixedAngle - limitingAngle) / (360f - 2 * limitingAngle)

                            onValueChanged(percent)
                            true

                        } else false
                    }
                    else -> false
                }
            }
            .rotate(rotation)
    )

}


@Composable
fun CircularLoader(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(durationMillis = animDuration, delayMillis = animDelay)
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2)
    ) {

        Canvas(modifier = Modifier.size(radius * 2)) {

            drawArc(
                color = color,
                -90f,
                360 * curPercentage.value, useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

        }

        Text(
            text = (curPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )

    }


}


@Composable
private fun SimpleAnimation() {

    var state by remember {
        mutableStateOf(200.dp)
    }

    val size by animateDpAsState(targetValue = state,
        keyframes {
            durationMillis = 5000
            state at 0 with LinearEasing
            state * 1.5f at 1000 with FastOutLinearInEasing

            state * 2 at 5000

        }


        /*spring(Spring.DampingRatioHighBouncy)*/
        /*tween(3000,delayMillis = 1000, easing = LinearOutSlowInEasing)*/
    )


    val infinityTransition = rememberInfiniteTransition()

    val color by infinityTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(tween(2000), repeatMode = RepeatMode.Reverse)
    )

    Box(
        modifier = Modifier
            .background(color)
            .size(size), contentAlignment = Alignment.Center
    ) {

        Button(onClick = { state += 50.dp }) {
            Text(text = "Increase Size")
        }

    }
}

@Composable
fun MyComposable() {

    val scoffoledState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    Scaffold(scaffoldState = scoffoledState) {
//        var counter by remember {
//            mutableStateOf(0)
//        }

        var counter = produceState(initialValue = 0) {
            kotlinx.coroutines.delay(4000L)
            value = 4
        }

        if (counter.value % 5 == 0 && counter.value > 0) {


//            scope.launch {
            LaunchedEffect(key1 = scoffoledState.snackbarHostState) {
                scoffoledState.snackbarHostState.showSnackbar("Hello  ")
            }
        }

        Button(onClick = { /*counter++*/ }) {
            Text(text = "Click me ${counter.value}")

        }
    }


}


@Composable
private fun COnstraintLayoutCheck() {

    val constraint = ConstraintSet {
        val greenBox = createRefFor("greenbox")
        val redBox = createRefFor("redbox")

        constrain(greenBox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)

        }

        constrain(redBox) {
            top.linkTo(parent.top)
            start.linkTo(greenBox.end)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }

    }


    ConstraintLayout(constraintSet = constraint, modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId("greenbox")
        )
        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redbox")
        )
    }


}

@Composable
private fun LazyColums() {

    LazyColumn() {

        itemsIndexed(
            listOf("This", "is", "expected", "error")
        ) { index, string ->
            Text(
                text = string,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center
            )
        }

        items(5000) {

        }
    }
}

@Composable
fun SnackBarAndText() {

    var scaffolldState = rememberScaffoldState()
    var textFieldState by remember {
        mutableStateOf("")
    }

    var scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffolldState) {

        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            TextField(value = textFieldState,
                label = {
                    Text(text = "Enter your name")
                }, onValueChange = {
                    textFieldState = it
                })

            Spacer(modifier = Modifier.padding(10.dp))

            Button(onClick = {
                scope.launch {
                    scaffolldState.snackbarHostState.showSnackbar(
                        "Hello  $textFieldState",
                        actionLabel = "close"
                    )
                }
            }) {

                Text(text = "Plese greet me")
            }
        }
    }

}

@Composable
fun ColourBox(
    modifier: Modifier = Modifier,
    updateColour: (Color) -> Unit
) {


    Box(modifier = modifier
        .background(Color.Red)
        .clickable {
            updateColour.invoke(
                Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
        }) {

    }
}

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier.height(200.dp)
        ) {

            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, Color.Black
                            ), startY = 300f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }

    }

}