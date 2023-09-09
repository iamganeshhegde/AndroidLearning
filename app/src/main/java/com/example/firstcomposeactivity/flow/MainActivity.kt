package com.example.firstcomposeactivity.flow

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringDef
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.firstcomposeactivity.R
import com.example.firstcomposeactivity.ui.theme.FirstComposeActivityTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import java.util.*
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {


    // Return the NavigationBar height in pixels if it is present, otherwise return 0
    fun getNavigationBarHeight(activity: Activity): Int {
        val rectangle = Rect()
        val displayMetrics = DisplayMetrics()
        activity.window.decorView.getWindowVisibleDisplayFrame(rectangle)
        activity.windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        return displayMetrics.heightPixels - (rectangle.top + rectangle.height())
    }
    @SuppressLint("ResourceType", "InternalInsetResource")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val resources: Resources = this.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
       if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
           Log.d("Ganesh",
               "resources.getDimensionPixelSize(resourceId) ${resources.getDimensionPixelSize(resourceId)}"
           )
        } else {
           Log.d("Ganesh",
               "resources.getDimensionPixelSize(resourceId) - 0"
           )
       }


        Log.d("Ganesh",
            "resources.getDimensionPixelSize(resourceId) activity - ${getNavigationBarHeight(this)}"
        )



        this.navigationBarHeight


        Log.d("Ganesh",
            "nav bar ${this.navigationBarHeight}"
    )


        val bar = this.navigationBarHeight




        Log.d("Ganesh",
            "bar to dp  - ${bar.toFloat().convertPxToDp(this)}"
        )


        lifecycleScope.launch {

        }
        setContent {
            FirstComposeActivityTheme {
                val viewModel = viewModel<MainViewModel>()
                val count = viewModel.stateFlow.collectAsState(initial = 0)


                var expanded: Boolean? by remember { mutableStateOf(false) }


                var list = listOf<Int>(1, 2, 3, 4, 5)



                LaunchedEffect(key1 = true) {
                    viewModel.sharedFlow.collect { number ->

                    }
                }

                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                val screenWidthPx = with(LocalDensity.current) { screenWidth.toPx() }
                val screenHeightPx = with(LocalDensity.current) { screenHeight.toPx() }

                val first = IntOffset((screenWidthPx / 2).toInt(), (screenHeightPx / 2).toInt())
                val veryFirst = IntOffset(100, 100)

                var targetOffset: IntOffset = IntOffset(800, 400)


                val startOffset = IntOffset(100, 500)


                var widthScale by remember {
                    mutableStateOf(false)
                }

                LaunchedEffect(key1 = true) {
                    delay(2000)
                    widthScale = true
                }


                /*var scaleUpWidth by animateDpAsState(
                    30.dp
                )*/

                var animStates by remember {
                    mutableStateOf(ParticipantGiftAnimationStates.INITIAL)
                }

                LaunchedEffect(key1 = true) {
                    delay(1000)
                    animStates = ParticipantGiftAnimationStates.SCALE_UP
                    delay(1000)
                    animStates = ParticipantGiftAnimationStates.SLIDE_OUT
                    delay(4000)
                    animStates = ParticipantGiftAnimationStates.SCALE_DOWN
                    delay(1000)
                    animStates = ParticipantGiftAnimationStates.INITIAL

                }

                val scale by animateFloatAsState(
                    targetValue = when (animStates) {
                        ParticipantGiftAnimationStates.INITIAL -> 1f
                        ParticipantGiftAnimationStates.SCALE_UP -> 2f
                        ParticipantGiftAnimationStates.SLIDE_OUT -> 2f
                        ParticipantGiftAnimationStates.SCALE_DOWN -> 0.5f
                        else -> 1f
                    },
                    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                )


                val width by animateDpAsState(
//                    targetValue = if(showAnim) {
//                        first
//                    } else {

                    when (animStates) {
                        ParticipantGiftAnimationStates.SCALE_UP -> 50.dp
                        ParticipantGiftAnimationStates.SLIDE_OUT -> 50.dp
                        ParticipantGiftAnimationStates.SCALE_DOWN -> 20.dp
                        else -> 30.dp
                    }


//                    if(widthScale) {
//                        30.dp
//                    } else {
//                        50.dp
//                    }

//                        targetOffset
                    /*}*/,
                    animationSpec = tween(
                        easing = LinearEasing
                    )
                    /*animationSpec = keyframes<Dp> {
                        durationMillis = 4000
                        50.dp at 0 with LinearEasing
                        30.dp at 500 with LinearEasing
                        30.dp at 3000 with LinearEasing
                        30.dp at 3500 with LinearEasing
                        10.dp at 3800 with LinearEasing
                    }*/
                )


                val offsetAnimation by animateIntOffsetAsState(
                    targetValue = when (animStates) {
                        ParticipantGiftAnimationStates.INITIAL -> startOffset
                        ParticipantGiftAnimationStates.SCALE_UP -> startOffset
                        ParticipantGiftAnimationStates.SLIDE_OUT -> targetOffset
                        ParticipantGiftAnimationStates.SCALE_DOWN -> targetOffset
                        else -> targetOffset
                    },
                    animationSpec = /*when(animStates)  {
                        ParticipantGiftAnimationStates.SCALE_UP -> {
                            tween(
                                durationMillis = 1000,
                                easing = LinearEasing
                            )
                        }
                        ParticipantGiftAnimationStates.SLIDE_OUT -> {
                            tween(
                                durationMillis = 4000,
                                easing = LinearEasing
                            )
                        }
                        ParticipantGiftAnimationStates.SCALE_DOWN -> {
                            tween(
                                durationMillis = 1000,
                                easing = LinearEasing
                            )
                        }
                        else -> {
                            tween(
                                durationMillis = 1000,
                                easing = LinearEasing
                            )
                        }

                    } */keyframes {
                        when (animStates) {
                            ParticipantGiftAnimationStates.SCALE_UP -> {
                                durationMillis = 1000
                                startOffset at 1000 with LinearEasing
                            }
                            ParticipantGiftAnimationStates.SLIDE_OUT -> {
                                durationMillis = 4000
                                targetOffset at 4000 with LinearEasing
                            }
                            ParticipantGiftAnimationStates.SCALE_DOWN -> {
                                durationMillis = 4000
                                targetOffset at 1000 with LinearEasing
                            }
                            else -> {

                            }
                        }
                    }) {
//                    animationCompleted.invoke()
                }


                var boxAnimState1 by remember {
                    mutableStateOf(false)
                }

                var showAnim by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(key1 = true) {
                    showAnim = true
                }

//                targetOffset = getQuadrantCenterOffset(broadcasterPosition = 2,
//                    broadcastersCount = 4,
//                    screenWidth = screenWidthPx,
//                    screenHeight = screenHeightPx)
//

                var negativeOffset = IntOffset(100, -600)

                var boxPosition by remember { mutableStateOf(BoxPosition.TopLeft) }


                val transition = updateTransition(targetState = boxPosition)


                val boxOffset by transition.animateOffset(
                    transitionSpec = {
                        tween(durationMillis = 1_000)
                    }
                ) { position ->
                    when (position) {
                        BoxPosition.TopLeft -> Offset(0F, 0F)
                        BoxPosition.BottomRight -> Offset(120F, 120F)
                        BoxPosition.TopRight -> Offset(120F, 0F)
                        BoxPosition.BottomLeft -> Offset(0F, 120F)
                    }
                }

                val xOffset by animateOffsetAsState(targetValue = Offset(targetOffset.x.toFloat(),
                    targetOffset.y.toFloat())) {
                    IntOffset(100, 200)
                }

                var currentX: Int
                var currentY: Int
                var inParentOffset: Offset = Offset(0f, 0f)
                var imageOffset: Offset = Offset(0f, 0f)


                var finalDestination by remember {
//                    mutableStateOf(first)
                    mutableStateOf(veryFirst)
                }

                LaunchedEffect(key1 = Unit) {
                    delay(2000)

                    targetOffset =
                        getQuadrantCenterOffset(
                            broadcasterPosition = 3,
                            broadcastersCount = 4,
                            screenWidth = screenWidthPx,
                            screenHeight = screenHeightPx,
                        )

                    finalDestination = targetOffset

//                    finalDestination = targetOffset

                }


                val animateOffset by animateIntOffsetAsState(
//                    targetValue = if(showAnim) {
//                        first
//                    } else {
                    finalDestination
//                    offsetAnimation
//                        targetOffset
                    /*}*/,
                    animationSpec = keyframes<IntOffset> {
//                        if(animStates == 2) {
                        durationMillis = 4000
//                            first at 200 with LinearEasing
                        veryFirst at 200 with LinearEasing
                        finalDestination at 3000 with LinearEasing
//                        }

                    }
                )





                LaunchedEffect(key1 = Unit) {
                    boxAnimState1 = true
                    delay(4000L)

                    boxAnimState1 = false
                }


                Button(onClick = {
                    boxPosition = getNextPosition(boxPosition)
                }) {
                    Text(text = "Click to get next position")
                }



                Column() {

                    Box(
                        modifier = Modifier
                            .layoutId("sendGiftBtn")
                            .clip(CircleShape)
                            .background(Color(0xff0000ff))
                            .size(32.dp)

                    ) {

                        Image(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(start = 4.dp)
                                .size(18.dp),
//                painter = rememberAsyncImagePainter(boxImageState?.bitmap),
                            painter = rememberImagePainter("https://cdn-stag.sharechat.com/a18a844_1658921987455.png"),
                            contentDescription = "some useful description",
                            colorFilter = ColorFilter.tint(Color(0xffFFFFFF))
                        )
                    }

                    Box(modifier = Modifier) {
                        Button(
                            onClick = {
                                viewModel.incrementCOunter()
                            }
                        ) {
                            Text(text = "Counter ${count.value}")
                        }
                    }
/*


THis is for text layout overflow
                    Row(Modifier) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "asvjasvjcvbcdsabcjdvascvjdhavjavdmavsmdavsdvakshvdkhavd mqa dcabnd vcjeav cjhds cjsd mc kabdka",
                            maxLines = if (expanded == true) Int.MAX_VALUE else 1,
                            overflow = TextOverflow.Ellipsis,
                            onTextLayout = { textLayoutResult ->

                                *//* if (textLayoutResult.hasVisualOverflow) {

                                     val lineindex = textLayoutResult.getLineEnd(0, visibleEnd = true)
                                 }*//*
                                if (textLayoutResult.isLineEllipsized(0)) {
                                    expanded = false
                                }

                                Log.d("Ganesh",
                                    "lineCount = ${textLayoutResult.lineCount}, isLineEllipsized = ${
                                        textLayoutResult.isLineEllipsized(0)
                                    }, textLayoutResult.didOverflowWidth ${textLayoutResult.didOverflowWidth}")
                            },
                        )
                        if (expanded?.not() == true) {
                            Text(
                                text = "More",
                                modifier = Modifier.clickable(enabled = expanded?.not() == true) {
                                    expanded = true
                                },
                            )
                        }
                    }*/

/*

THis is offset check
                    Box(modifier = Modifier
                        .size(200.dp)
                        .background(Color.Black)
                    ) {
                        Box(modifier = Modifier
                            .offset(boxOffset.x.dp, boxOffset.y.dp)
                            .size(30.dp)
                            .background(Color.Yellow))
                    }*/


/*


                    androidx.compose.animation.AnimatedVisibility(
                        modifier = Modifier
                            .background(color = Color.Red.copy(alpha = 0.2f))
                            .wrapContentSize(),
                        */
/*.offset {
                            first
                        }*//*

                        visible = boxAnimState1,
                        */
/*enter = *//*
*/
/*scaleIn(initialScale = 0.5f ) +*//*
*/
/* fadeIn(
                            animationSpec = tween(durationMillis = 200)
                        ),
                        exit = *//*
*/
/*scaleOut(targetScale = 0.2f, animationSpec = keyframes {
                            durationMillis = 4000
                            2f at 0 with LinearEasing
                            1f at 500 with LinearEasing
                            1f at 3000 with LinearEasing
                            2f at 3500 with LinearEasing
                            0.2f at 3800 with LinearEasing
                        } ) +*//*
*/
/* slideOut(targetOffset = {
                            targetOffset
                        }, animationSpec =
                        keyframes {
                            durationMillis = 4000
                            first at 0 with LinearEasing
                            first at 500 with LinearEasing
                            targetOffset at 3000 with LinearEasing
                        }

                            *//*
*/
/*keyframes {
                                durationMillis = 4000
                                0f at
                                0f at 0 with LinearOutSlowInEasing // for 0-15 ms
                                1f at 1000 with FastOutLinearInEasing // for 15-75 ms
                                1f at 75 // ms
                                1.2f at 4000 // ms
                            }*//*
*/
/*
                        )*//*

                    ) {
                        Log.d("NormalSlabUI",
                            "slot 1 exit anim for self gift started - boxImageState?.bitmap }")
                        Log.d("NormalSlabUI",
                            "slot 1 exit anim for target offset - targetOffset ${targetOffset?.x}, ${targetOffset?.y}, screenWidthPx ${screenWidthPx}, screenHeightPx - ${screenHeightPx}")


                        Image(
                            modifier = Modifier.height(80.dp),
//                painter = rememberAsyncImagePainter(boxImageState?.bitmap),
                            painter = rememberImagePainter(R.drawable.ic_baseline_headset_24),
                            contentDescription = "some useful description",
                        )

                    }

*/

                }

                /*ConstraintLayout(constraintSet = ConstraintSet {

                    val image = createRefFor("image")

                    constrain(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        *//*bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)*//*
                    }
                }, modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Green.copy(alpha = 0.3f))) {


                    AnimatedVisibility(
                        visible = boxAnimState1,
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                inParentOffset = coordinates.positionInParent()
                                currentX = coordinates.positionInRoot().x.toInt()
                                currentY = coordinates.positionInRoot().x.toInt()


                                Log.d("NormalSlabUI",
                                    "coordinates.positionInParent() - ${coordinates.positionInRoot().x}, ${coordinates.positionInRoot().y}, ${currentX}, ${currentY}")

                            }
                            .absoluteOffset {
                                first
//                                targetOffset
//                                animateOffset
//                                xOffset
                            }

                            *//*.layoutId("image")*//*
                            .wrapContentSize()
                            .background(color = Color.Red.copy(alpha = 0.2f))
                            .background(color = Color.Blue.copy(alpha = 0.2f)),
                        enter = *//*scaleIn(initialScale = 0.5f ) +*//* fadeIn(
                            animationSpec = tween(durationMillis = 200)
                        ),
                        exit = *//*shrinkOut(animationSpec = keyframes {
                            durationMillis = 4000
                            IntSize(20, 30) at 0 with LinearEasing
                            IntSize(20, 30) at 500 with LinearEasing
                            IntSize(20, 30) at 3000 with LinearEasing
                            IntSize(50, 60) at 3000 with LinearEasing
                            IntSize(10, 20) at 3800 with LinearEasing
                        } ) + *//* *//*scaleOut(targetScale = 0.2f, animationSpec = keyframes {
                            durationMillis = 4000
                            2f at 0 with LinearEasing
                            1f at 500 with LinearEasing
                            1f at 3000 with LinearEasing
                            2f at 3500 with LinearEasing
                            0.2f at 3800 with LinearEasing
                        } ) + *//*slideOut(targetOffset = {
//                            targetOffset
                            negativeOffset
                        }, animationSpec =
                        keyframes {
                            durationMillis = 4000
//                            first at 0 with LinearEasing
//                            first at 500 with LinearEasing
//                            targetOffset at 3000 with LinearEasing
                            negativeOffset at 3000 with LinearEasing
                        }
                        )) {

                        Log.d("NormalSlabUI",
                            "slot 1 exit anim for self gift started - boxImageState?.bitmap }")
                        Log.d("NormalSlabUI",
                            "slot 1 exit anim for target offset - targetOffset ${targetOffset?.x}, ${targetOffset?.y}, screenWidthPx ${screenWidthPx}, screenHeightPx - ${screenHeightPx}, first - ${first.x}, ${first.y}, inParentOffset - inParentOffset ${inParentOffset.x} ${inParentOffset.y}")


                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .onGloballyPositioned { coordinates ->
                                    imageOffset = coordinates.positionInParent()
                                    currentX = coordinates.positionInRoot().x.toInt()
                                    currentY = coordinates.positionInRoot().x.toInt()


                                    if (finalDestination.x != 0) {
                                        finalDestination = IntOffset(currentX, currentY)
                                        animStates = 2
                                    }

                                    targetOffset =
                                        getAbsoluteOffsetFromScreen(broadcasterPosition = 2,
                                            broadcastersCount = 4,
                                            screenWidth = screenWidthPx,
                                            screenHeight = screenHeightPx,
                                            currentOffset = IntOffset(imageOffset.x.toInt(),
                                                imageOffset.y.toInt())
                                        )

                                    Log.d("NormalSlabUI",
                                        "Image view coordinates.positionInParent() - ${coordinates.positionInRoot().x}, ${coordinates.positionInRoot().y}, ${currentX}, ${currentY}")

                                },
//                painter = rememberAsyncImagePainter(boxImageState?.bitmap),
                            painter = rememberImagePainter(R.drawable.ic_baseline_headset_24),
                            contentDescription = "some useful description",
                        )
                    }

                }
*/
/*

THis is for displaying movement if image

                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Blue)) {


                    Image(
                        modifier = Modifier
                            .padding(100.dp)
                            .align(Alignment.TopCenter)
                            .background(color = Color.Red.copy(alpha = 0.4f))
                            .size(40.dp)
                            .onGloballyPositioned {
                                it.boundsInParent()
//                                finalDestination = IntOffset(it.positionInRoot().x.toInt(),it.positionInRoot().y.toInt())
                            },
                        painter = rememberImagePainter(R.drawable.ic_baseline_bubble_chart_24),
                        contentDescription = "some useful description",
                    )


                    AnimatedVisibility(
                        modifier = Modifier.absoluteOffset {
//                                animateOffset
                            offsetAnimation
                        },
                        visible = animStates != ParticipantGiftAnimationStates.INITIAL,
                        enter = fadeIn(0.1f, animationSpec = tween(2000)),
                        exit = fadeOut(0.1f, animationSpec = tween(2000))

                    ) {
                        Image(
                            modifier = Modifier
                                .background(color = Color.Red.copy(alpha = 0.4f))
//                            .width(scaleUpWidth)
//                            .width(width)
                                .width(30.dp)
//                            .height(width)
                                .height(30.dp),
                            *//*.absoluteOffset {
//                                animateOffset
                                offsetAnimation
                            }*//*
                            painter = rememberImagePainter(R.drawable.ic_baseline_person_24),
                            contentDescription = "some useful description",
                        )
                    }

                }*/




                /*Column(Modifier) {
                    Spacer(modifier = Modifier.padding(top = 300.dp))
                    ParticipantsStrip(modifier = Modifier)
                    FullWebViewUI()
                }*/


//                TranslatingObjects()

                val scope = rememberCoroutineScope()

                var sourceOffset by remember {
                    mutableStateOf(Offset(0f, 0f))
                }
                var destinationOffset by remember {
                    mutableStateOf(Offset(0f, 0f))
                }

                var translatingObjectsCount by remember {
                    mutableStateOf(0)
                }

                val translateAnimatable = remember {
                    Animatable(initialValue = 0f)
                }

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    for (i in 1..translatingObjectsCount) {
                        NewTranslatingObject(
                            startDestination = sourceOffset,
                            endDestination = destinationOffset,
                            content = {
                                Box(
                                    modifier = Modifier
                                ){
                                    Text(text = "\uD83E\uDD2A",)
                                }
                            },
                            onProgressUpdateListener = {

                            }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        MyHorizontalList(translateAnimatable) { offset, size ->
                            sourceOffset = Offset(
                                x = offset.x + (size.width / 2f) - 20f,
                                y = offset.y + (size.height / 2f) - 40f
                            )
                        }

                        Spacer(modifier = Modifier.height(48.dp))


                        MyHorizontalRow() { offset, size ->
                            destinationOffset = Offset(
                                x = offset.x + (size.width / 2f) - 20f,
                                y = offset.y + (size.height / 2f) - 40f
                            )
                        }

                        Spacer(modifier = Modifier.height(56.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    translatingObjectsCount = 0
                                    translateAnimatable.animateTo(0f)
                                    translateAnimatable.animateTo(
                                        targetValue = 1f,
                                        animationSpec = tween(durationMillis = 3500)
                                    ) {
                                        translatingObjectsCount = (this.value * 10).roundToInt()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Start Animation \uD83E\uDD2A")
                        }
                    }
                }
            }
        }
    }
}


fun getQuadrantCenterOffset(
    broadcasterPosition: Int,
    broadcastersCount: Int,
    screenWidth: Float,
    screenHeight: Float,
): IntOffset {

    return when (broadcastersCount) {
        1 -> IntOffset((screenWidth / 2).toInt(), (screenHeight / 2).toInt())
        2 -> {
            when (broadcasterPosition) {
                0 -> IntOffset((screenWidth / 2).toInt(), (screenHeight / 4).toInt())
                1 -> IntOffset((screenWidth / 2).toInt(), 3 * (screenHeight / 4).toInt())
                else -> {
                    IntOffset(-1, -1)
                }
            }
        }
        3 -> {
            when (broadcasterPosition) {
                0 -> IntOffset((screenWidth / 2).toInt(), (screenHeight / 4).toInt())
                1 -> IntOffset((screenWidth / 4).toInt(), 3 * (screenHeight / 4).toInt())
                2 -> IntOffset(3 * (screenWidth / 4).toInt(), 3 * (screenHeight / 4).toInt())
                else -> {
                    IntOffset(-1, -1)
                }
            }
        }
        4 -> {
            when (broadcasterPosition) {
                0 -> IntOffset((screenWidth / 4).toInt(), (screenHeight / 4).toInt())
                1 -> IntOffset(3 * (screenWidth / 4).toInt(), (screenHeight / 4).toInt())
                2 -> IntOffset((screenWidth / 4).toInt(), 3 * (screenHeight / 4).toInt())
                3 -> IntOffset(3 * (screenWidth / 4).toInt(), 3 * (screenHeight / 4).toInt())
                else -> {
                    IntOffset(-1, -1)
                }
            }
        }
        else -> {
            IntOffset(-1, -1)
        }
    }

}


/*
From other app



        androidx.compose.animation.AnimatedVisibility(
            modifier = Modifier
                .wrapContentSize()
//                .layoutId("gift_to_participant")
                .background(color = Color.Red.copy(alpha = 0.5f))
                /*.offset {
                    startOffset
                }*/,
//            visible = display,
//            visible = startExitAnimation,
            visible = false,
            enter = scaleIn(initialScale = 0.5f) + fadeIn(
                animationSpec = tween(durationMillis = AnimDuration.SELF_GIFT_ENTER)
            ),
            exit = scaleOut(targetScale = 0.2f, animationSpec = keyframes {
                durationMillis = 4000
                2f at 100 with LinearEasing
                1f at 200 with LinearEasing
                1f at 3000 with LinearEasing
                2f at 3500 with LinearEasing
                0.2f at 3800 with LinearEasing
            }) + slideOut(targetOffset = {
//                targetOffset
//                Logger.d("NormalSlabUI",
//                    "inside slideout target(startOffset) - ${target(startOffset)},startOffset - ${startOffset.x}, ${startOffset.y} cohostNumber ${cohostNumber()}, participantCount ${participantCount()}")

//                target(startOffset)
                finalTarget
//                targetOfCohostAnim
//                cohostOffset
            }, animationSpec =
            keyframes {
                durationMillis = 4000
//                    first at 0 with LinearEasing
//                    first at 500 with LinearEasing
//                targetOffset at 3000 with LinearEasing
//                target(currentImagePosition) at 3000 with LinearEasing
//                targetOfCohostAnim at 3000 with LinearEasing
//                cohostOffset at 3000 with LinearEasing

//                Logger.d("NormalSlabUI",
//                    "inside keyframes target(startOffset) - ${target(startOffset)}, finalTarget ${finalTarget.x} ${finalTarget.y}")



//                target(startOffset) at 3000 with LinearEasing
                finalTarget at 3000 with LinearEasing
            }

                /*keyframes {
                    durationMillis = 4000
                    0f at
                    0f at 0 with LinearOutSlowInEasing // for 0-15 ms
                    1f at 1000 with FastOutLinearInEasing // for 15-75 ms
                    1f at 75 // ms
                    1.2f at 4000 // ms
                }*/
            )
        ) {
            Logger.d("NormalSlabUI",
                "slot 1 exit anim for self gift started - boxImageState?.bitmap ${boxImageState?.bitmap}")
            Logger.d("NormalSlabUI",
                "slot 1 exit anim for target offset - targetOffset ${targetOffset?.x}, ${targetOffset?.y}, screenWidthPx ${screenWidthPx}, screenHeightPx - ${screenHeightPx}")


            Image(
                modifier = Modifier
                    .height(30.dp)
                    .onGloballyPositioned {
                        Logger.d("NormalSlabUI",
                            "inside onglobally positioned outside condition  slab - ${slab}, it.positionInRoot(). - ${it.positionInRoot().x}, ${it.positionInRoot().y},  it.positionInWindow. - ${it.positionInWindow().x}, ${it.positionInWindow().y}")

                        if (displayCohostAnimation) {
                            startOffset = IntOffset(
                                x = it.positionInRoot().x.toInt(),
                                y = it.positionInRoot().y.toInt()
                            )

                            finalTarget =  getAbsoluteOffsetFromScreen(broadcasterPosition = cohostNumber(),
                                broadcastersCount = participantCount(),
                                screenWidth = screenWidthPx,
                                screenHeight = screenHeightPx,
                                currentOffset = startOffset)

                            displayCohostAnimation = true

                            startExitAnimation = true

                            Logger.d("NormalSlabUI",
                                "inside onglobally positioned  slab - ${slab}, startOffset - ${startOffset.x}, ${startOffset.y} ")
                        }
                    },
//                painter = rememberAsyncImagePainter(boxImageState?.bitmap),
                painter = rememberAsyncImagePainter(moj.feature.live_stream_ui.R.drawable.ic_person_empty),
                contentDescription = "some useful description",
            )
        }



 */


/*

val offsetAnimation by animateIntOffsetAsState(
    targetValue = when(animStates) {
        ParticipantGiftAnimationStates.SCALE_UP -> giftToParticipant.startOffset
        ParticipantGiftAnimationStates.SLIDE_OUT -> giftToParticipant.targetOffset
        ParticipantGiftAnimationStates.SCALE_DOWN -> giftToParticipant.targetOffset
        else -> giftToParticipant.targetOffset
    },
    animationSpec = keyframes {
        keyframes<IntOffset> {
            when(animStates) {
                ParticipantGiftAnimationStates.SCALE_UP -> {
                    durationMillis = 4000
                    giftToParticipant.startOffset at 200 with LinearEasing
                }
                ParticipantGiftAnimationStates.SLIDE_OUT -> {
                    giftToParticipant.targetOffset
                }
                ParticipantGiftAnimationStates.SCALE_DOWN -> {
                    giftToParticipant.targetOffset at 3000 with LinearEasing
                    giftToParticipant.targetOffset
                }
                else -> giftToParticipant.targetOffset
            }
        }
    }) {
    animationCompleted.invoke()
}*/

fun getAbsoluteOffsetFromScreen(
    broadcasterPosition: Int,
    broadcastersCount: Int,
    screenWidth: Float,
    currentOffset: IntOffset,
    screenHeight: Float,
): IntOffset {

    return when (broadcastersCount) {
        1 -> {
            var targetOffsetinRoot =
                IntOffset((screenWidth / 2).toInt(), (screenHeight / 2).toInt())
            return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                (targetOffsetinRoot.y - currentOffset.y))
        }
        2 -> {
            when (broadcasterPosition) {
                0 -> {
                    var targetOffsetinRoot =
                        IntOffset((screenWidth / 2).toInt(), (screenHeight / 4).toInt())
                    return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                        (targetOffsetinRoot.y - currentOffset.y))
                }
                1 -> {
                    var targetOffsetinRoot =
                        IntOffset((screenWidth / 2).toInt(), 3 * (screenHeight / 4).toInt())
                    return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                        (targetOffsetinRoot.y - currentOffset.y))
                }
                else -> {
                    IntOffset(-1, -1)
                }
            }
        }
        3 -> {
            when (broadcasterPosition) {
                0 -> {
                    var targetOffsetinRoot =
                        IntOffset((screenWidth / 2).toInt(), (screenHeight / 4).toInt())
                    return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                        (targetOffsetinRoot.y - currentOffset.y))

                }
                1 -> {
                    var targetOffsetinRoot =
                        IntOffset((screenWidth / 4).toInt(), 3 * (screenHeight / 4).toInt())
                    return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                        (targetOffsetinRoot.y - currentOffset.y))
                }
                2 -> {
                    var targetOffsetinRoot =
                        IntOffset(3 * (screenWidth / 4).toInt(), 3 * (screenHeight / 4).toInt())
                    return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                        (targetOffsetinRoot.y - currentOffset.y))
                }
                else -> {
                    IntOffset(-1, -1)
                }
            }
        }
        4 -> {
            when (broadcasterPosition) {
                0 -> {
                    var targetOffsetinRoot =
                        IntOffset((screenWidth / 4).toInt(), (screenHeight / 4).toInt())
                    return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                        (targetOffsetinRoot.y - currentOffset.y))

                }
                1 -> {
                    var targetOffsetinRoot =
                        IntOffset(3 * (screenWidth / 4).toInt(), (screenHeight / 4).toInt())
                    return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                        (targetOffsetinRoot.y - currentOffset.y))

                }
                2 -> {
                    var targetOffsetinRoot =
                        IntOffset((screenWidth / 4).toInt(), 3 * (screenHeight / 4).toInt())
                    return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                        (targetOffsetinRoot.y - currentOffset.y))

                }
                3 -> {
                    var targetOffsetinRoot =
                        IntOffset(3 * (screenWidth / 4).toInt(), 3 * (screenHeight / 4).toInt())
                    return IntOffset((targetOffsetinRoot.x - currentOffset.x),
                        (targetOffsetinRoot.y - currentOffset.y))
                }
                else -> {
                    IntOffset(-1, -1)
                }
            }
        }
        else -> {
            IntOffset(-1, -1)
        }
    }

}


enum class BoxPosition {
    TopRight,
    TopLeft,
    BottomRight,
    BottomLeft
}

fun getNextPosition(position: BoxPosition) =
    when (position) {
        BoxPosition.TopLeft -> BoxPosition.BottomRight
        BoxPosition.BottomRight -> BoxPosition.TopRight
        BoxPosition.TopRight -> BoxPosition.BottomLeft
        BoxPosition.BottomLeft -> BoxPosition.TopLeft
    }


@StringDef
@Retention(AnnotationRetention.SOURCE)
annotation class ParticipantGiftAnimationStates {
    companion object {

        const val INITIAL = "INITIAL"
        const val SCALE_UP = "SCALE_UP"
        const val SLIDE_OUT = "SLIDE_OUT"
        const val SCALE_DOWN = "SCALE_DOWN"
    }
}


@Composable
fun ParticipantsStrip(
    modifier: Modifier = Modifier,
) {
    val list = listOf<Int>(1, 2, 3, 4,5,6,7,8,9,10)


    ConstraintLayout(
        modifier = modifier
            .background(color = Color.Red.copy(alpha = 0.3f))
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 6.dp)
            .height(60.dp),
        constraintSet = ConstraintSet {
            val divider = createRefFor("divider")
            val icCross = createRefFor("icCross")
            val participants = createRefFor("participants")
            val sendGiftBtn = createRefFor("sendGiftBtn")
            val quantityCounter = createRefFor("quantityCounter")
            val quantityList = createRefFor("quantityList")
            val scrollable = createRefFor("scrollable")


            constrain(divider) {
                width = Dimension.fillToConstraints
            }
            constrain(participants) {
                top.linkTo(divider.bottom)
                start.linkTo(parent.start, 20.dp)
                bottom.linkTo(parent.bottom)
                end.linkTo(sendGiftBtn.start, 20.dp)
                width = Dimension.fillToConstraints
            }

            constrain(sendGiftBtn) {
                top.linkTo(divider.bottom)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
            constrain(scrollable) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, 50.dp)
            }
        },

    ) {


        ScrollableList(
            modifier = Modifier.layoutId("scrollable")
        )



        Divider(
            modifier = Modifier
                .layoutId("divider")
                .padding(horizontal = 8.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

        Button(
            modifier = Modifier
                .layoutId("sendGiftBtn")
                .size(40.dp)
                .background(color = Color.Gray),
            onClick = { }) {

        }


        /*list.forEachIndexed { index, item ->
//            SelectedParticipant(index, item)

            Row() {
                Image()
            }

        }*/

        val listState = rememberLazyListState()
        val scrollState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        val pxValue = with(LocalDensity.current) { 20.dp.toPx() }

        LaunchedEffect(key1 = Unit) {
            Log.d("Ganesh", "${scrollState.firstVisibleItemIndex}")
            if(scrollState.firstVisibleItemScrollOffset == 1) {

                Log.d("Ganesh", "${scrollState.firstVisibleItemScrollOffset}")
                scope.launch {
                    listState.animateScrollToItem(6)
                }
            }
        }

        LazyRow(
            modifier = Modifier
                .layoutId("participants")
                .wrapContentWidth()
                .background(color = Color.Green.copy(alpha = 0.2f)),
            state = listState,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            content = {

                itemsIndexed(list, key = { index, item -> index }) { index, item ->


                    ImageNew(index, item) {selectedIndex ->
                        if(selectedIndex == 4) {
                            scope.launch {
                                listState.animateScrollBy(3000f, animationSpec = tween(500))
//                                listState.animateScrollToItem(list.size-1)
                            }
                        }
                    }
                }
            })

        /*LazyRow(
            modifier = modifier
                .layoutId("participants"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(items = list) { index, item ->
                SelectedParticipant(index, item)
            }
        }*/

    }


}


@Composable
fun ScrollableList(modifier: Modifier = Modifier) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    LazyRow(
        state = scrollState,
        modifier = Modifier
            .padding(5.dp)
            .size(50.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        itemsIndexed(items = (0..10).toList()) { index, item ->


            Box() {


            }


         /*   Box(
                Modifier.focusable(

                )
            )
            onFocused = { focused ->
                scope.launch {
                    if (focused) {
                        val visibleItemsInfo = scrollState.layoutInfo.visibleItemsInfo
                        val visibleSet = visibleItemsInfo.map { it.index }.toSet()
                        if (index == visibleItemsInfo.last().index) {
                            scrollState.scrollToItem(index)
                        } else if (visibleSet.contains(index) && index != 0) {
                            scrollState.scrollToItem(index - 1)
                        }
                    }
                }
            })*/
        }
    }
}


@Composable
fun ImageNew(index: Int, item: Int, selectedIndex: (selectedIndex: Int) -> Unit) {
    Image(painter = rememberImagePainter(data = R.drawable.ic_baseline_person_24),
        contentDescription = "",
        modifier = Modifier
            .size(40.dp)
            .background(color = Color.Yellow.copy(alpha = 0.2f))
            .clickable {
                selectedIndex.invoke(index)
            })

}

@Composable
fun SelectedParticipant(
    index: Int,
    item: Int,
) {


    /*  var selectedIndex by remember {
          mutableStateOf(false)
      }

      val modifier = if (selectedIndex) {
          Modifier
              .wrapContentWidth()
              .wrapContentHeight()
              .clip(CircleShape)
              .background(Color.White)
      } else {
          Modifier
              .wrapContentWidth()
              .wrapContentHeight()
              .background(Color.Green.copy(alpha = 0.2f))
      }
  */



    Box(/*modifier = modifier.clickable {
        selectedIndex = selectedIndex.not()
    }*/) {
        Image(painter = rememberImagePainter(
            data = R.drawable.ic_baseline_person_24),
            contentDescription = "person",
            modifier = Modifier.size(40.dp)
        )
    }


}


val Context.navigationBarHeight: Int
    get() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (Build.VERSION.SDK_INT >= 30) {
            windowManager
                .currentWindowMetrics
                .windowInsets
                .getInsets(WindowInsets.Type.navigationBars())
                .bottom

        } else {
            val currentDisplay = try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    display
                } else {
                    TODO("VERSION.SDK_INT < R")
                }
            } catch (e: NoSuchMethodError) {
                windowManager.defaultDisplay
            }

            val appUsableSize = Point()
            val realScreenSize = Point()
            currentDisplay?.apply {
                getSize(appUsableSize)
                getRealSize(realScreenSize)
            }

            // navigation bar on the side
            if (appUsableSize.x < realScreenSize.x) {
                return realScreenSize.x - appUsableSize.x
            }

            // navigation bar at the bottom
            return if (appUsableSize.y < realScreenSize.y) {
                realScreenSize.y - appUsableSize.y
            } else 0
        }
    }


fun Float.convertPxToDp(context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return this * (160F / metrics.densityDpi)
}


//fun main() = runBlocking {
    /*val template = listOf("4", "3"r, "1", "2")
    val list = mutableListOf(Pair("3", "C"), Pair("1", "A"), Pair("4", "D")*//*, Pair("2", "B")*//*)
    print(template)
    print(list)

    list.sortBy {view ->
//        template.indexOf(it.first)
        template.indexOfFirst {
            it.toInt() == view.first.toInt()
        }
    }
    println(list)


     val winner = select<String> {
         data2().onAwait { it }
            data1().onAwait { it }

        }
    println(winner)




    val evenChannel = GlobalScope.produce<Int> {
        repeat(10) {
            delay(1000)
            if (it % 2 == 0) {
                send(it)
            }
        }
    }
    val oddChannel = GlobalScope.produce<Int> {
        repeat(10) {
            delay(500)
            if (it % 2 != 0) {
                send(it)
            }
        }
    }

    repeat(5) {
        val number = select<Int> {
            evenChannel.onReceive { it }
            oddChannel.onReceive { it }
        }
        println(number) // prints "0 1 3 2 5"
    }

*/

    /*val channel = Channel<Int>()
    launch {
        repeat(5) { index ->
            delay(1000)
            println("Producing next one")
            channel.send(index * 2)
        }
        channel.close()
    }


    launch {

            for (element in channel) {
                println("received - $element")
            }
    }

    *//*GlobalScope.launch {
        receiver()
    }*//*



    val channel2 = produce {
        repeat(5) { index ->
            println("Producing 2nd type next one")
            delay(1000)
            send(index * 3)
        }
    }

    for (element in channel2) {
        println(element)
    }*/

/*
    val channel3 = produce(capacity = Channel.CONFLATED) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }

    delay(1000)
    for (element in channel3) {
        println(element)
        delay(1000)
    }*/


    /*val channel = produceNumbers()
    repeat(3) { id ->
        delay(10)
        launchProcessor(id, channel)
    }*/

/*

    val channel = Channel<String>()
    launch { sendString(channel, "foo", 200L) }
    launch { sendString(channel, "BAR!", 500L) }
    repeat(50) {
        println(channel.receive())
    }
    coroutineContext.cancelChildren()
*/

//    println()




//}

fun main() = runBlocking{


    val list = asyncStringsList()
    val result = select<String> {
        list.withIndex().forEach { (index, deferred) ->
            deferred.onAwait { answer ->
                "Deferred $index produced answer '$answer'"
            }
        }
    }
    println(result)
    val countActive = list.count { it.isActive }
    println("$countActive coroutines are still active")

    /*val side = Channel<Int>() // allocate side channel
    launch { // this is a very fast consumer for the side channel
        side.consumeEach { println("Side channel has $it") }
    }
    produceNumbers(side).consumeEach {
        println("Consuming $it")
        delay(250) // let us digest the consumed number properly, do not hurry
    }
    println("Done consuming")
    coroutineContext.cancelChildren()
*/




/*
    val a = produce<String> {
        repeat(4) { send("Hello $it") }
    }
    val b = produce<String> {
        repeat(4) { send("World $it") }
    }
    repeat(8) { // print first eight results
        println(selectAorB(a, b))
    }
    coroutineContext.cancelChildren()*/

   /* val fizz = fizz()
    val buzz = buzz()
    repeat(17) {
        selectFizzBuzz(fizz, buzz)
    }
    coroutineContext.cancelChildren() // cancel fizz & buzz coroutines*/
}


fun CoroutineScope.asyncStringsList(): List<Deferred<String>> {
    val random = Random(3)
    return List(12) { asyncString(random.nextInt(1000)) }
}

fun CoroutineScope.asyncString(time: Int) = async {
    delay(time.toLong())
    "Waited for $time ms"
}
fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int> {
    for (num in 1..10) { // produce 10 numbers from 1 to 10
        delay(100) // every 100 ms
        select<Unit> {
            onSend(num) {} // Send to the primary channel
            side.onSend(num) {} // or to the side channel
        }
    }
}


suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>): String =
    select<String> {

        b.onReceiveCatching { it ->
            val value = it.getOrNull()
            if (value != null) {
                "b -> '$value'"
            } else {
                "Channel 'b' is closed"
            }
        }

        a.onReceiveCatching { it ->
            val value = it.getOrNull()
            if (value != null) {
                "a -> '$value'"
            } else {
                "Channel 'a' is closed"
            }
        }

    }


suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
    select<Unit> {
        fizz.onReceive {value ->
            println("fizz -> '$value'")
        }

        buzz.onReceive { value ->  // this is the second select clause
            println("buzz -> '$value'")
        }
    }
}
fun CoroutineScope.fizz() = produce<String> {
    while (true) { // sends "Fizz" every 300 ms
        delay(300)
        send("Fizz")
    }
}


fun CoroutineScope.buzz() = produce<String> {
    while (true) { // sends "Fizz" every 300 ms
        delay(500)
        send("Buzz")
    }
}
suspend fun sendString(
    channel: SendChannel<String>,
    text: String,
    time: Long
) {
    while (true) {
        delay(time)
        channel.trySend(text)
    }
}

fun CoroutineScope.produceNumbers() = produce {
    repeat(10) {
        delay(1000)
        send(it)
    }
}

fun CoroutineScope.launchProcessor(
    id: Int,
    channel: ReceiveChannel<Int>
) = launch {
    delay(id*1000L)
    for (msg in channel) {
        println("#$id received $msg")
    }
}

fun CoroutineScope.produceNumbers(
    max: Int
): ReceiveChannel<Int> = produce {
    var x = 0
    while (x < 5) send(x++)
}

suspend fun receiver() = coroutineScope{
    val channel = Channel<Int>()
    launch {
        repeat(5) { index ->
            delay(1000)
            println("Producing next one")
            channel.send(index * 2)
        }
    }


    launch {
        repeat(5) {
            val received = channel.receive()
            println("received - $received")
        }
    }
}

fun data1() = GlobalScope.async {
    delay(5000)
    "Hello"
}
fun data2() = GlobalScope.async {
    delay(2000)
    "World"
}