package com.example.firstcomposeactivity.noteapp.feature_note.presentation

import android.app.PictureInPictureParams
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.firstcomposeactivity.R
import com.example.firstcomposeactivity.noteapp.feature_note.presentation.notes.components.GenericExoPlayer
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.EventLogger
import kotlinx.coroutines.launch
import java.util.*

class NoteMainActivity : ComponentActivity() {

//    var player: SimpleExoPlayer? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*


        var b = A()




        getExoPlayer()

        setContent {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    toPictureInPictureMode()
                }) {


                *//*GenericExoPlayer(exoPlayer = player, modifier = Modifier.fillMaxSize(), onUpdate = {
                    play()
                })*//*
                Column {
                    Text(text = *//*"This is the current screen PIP"*//* b.print())
                    *//*Button(onClick = { toPictureInPictureMode() }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_height),
                            contentDescription = "Immersive"
                        )
                    }*//*

                }
            }
        }*/


        /*val heartCount = remember { mutableStateOf(0) }

        Box(modifier = Modifier.fillMaxSize()) {
            repeat(heartCount.value) {
                Heart(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 36.dp),
                    horizontalPadding = 24,
                    bottomMargin = 110
                )
            }

            Button(
                onClick = {
                    heartCount.value++
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                Text(
                    text = "Like",
                    color = Color.White
                )
            }

        }*/


}
/*

@RequiresApi(Build.VERSION_CODES.O)
private fun toPictureInPictureMode() {

    val width = window.decorView.width
    val height = window.decorView.height
    val rational = Rational(width, height)

    var builder = PictureInPictureParams.Builder()
    builder.setAspectRatio(rational).build()

    enterPictureInPictureMode(builder.build())

    */
/*val d: Display = windowManager
        .defaultDisplay
    val p = Point()
    d.getSize(p)
    val width: Int = p.x
    val height: Int = p.y*//*

}
*/

/*private fun getExoPlayer() {


    val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
    val trackSelector = DefaultTrackSelector(this, videoTrackSelectionFactory)
    val loadControlBuilder = DefaultLoadControl.Builder()
    loadControlBuilder.setBufferDurationsMs(
        2000,
        10000,
        1000,
        2000
    )
    loadControlBuilder.setPrioritizeTimeOverSizeThresholds(false)

    player = SimpleExoPlayer
        .Builder(this)
        .setLoadControl(loadControlBuilder.build())
        .setTrackSelector(trackSelector)
        .build()

    player?.apply {
        prepare()
    }



}

    fun play() {
        player!!.setMediaItem(MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"))

        player!!.play()
    }*/
}

/*
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Heart(modifier: Modifier, horizontalPadding: Int, bottomMargin: Int) {
    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp - bottomMargin

    val yRandom = kotlin.random.Random.nextInt(0, height / 2)
    val xRandom = kotlin.random.Random.nextInt(horizontalPadding, (width - horizontalPadding))


    val state = remember {
        mutableStateOf(HeartState.Show)
    }

    val offsetYAnimation: Dp by animateDpAsState(
        targetValue = when (state.value) {
            HeartState.Show -> height.dp
            else -> yRandom.dp
        },
        animationSpec = tween(1000)
    )

    val offsetXAnimation: Dp by animateDpAsState(
        targetValue = when (state.value) {
            HeartState.Show -> (((width - (horizontalPadding * 2)) / 2) + 8).dp
            else -> xRandom.dp
        },
        animationSpec = tween(1000)
    )

    LaunchedEffect(key1 = state, block = {
        state.value = when (state.value) {
            HeartState.Show -> HeartState.Hide
            HeartState.Hide -> HeartState.Show
        }
    })

    AnimatedVisibility(
        visible = state.value == HeartState.Show,
        enter = fadeIn(animationSpec = tween(durationMillis = 250)),
        exit = fadeOut(animationSpec = tween(durationMillis = 700))
    ) {
        Canvas(modifier = modifier
            .offset(y = offsetYAnimation, x = offsetXAnimation),
            onDraw = {
                val path = Path().apply {
                    heartPath(Size(120f, 120f))
                }
                drawPath(
                    path = path,
                    color = Color.Red,
                )
            }
        )
    }
}*/


open class A() {
    open fun print(): String {
        return "Subba"

        Log.i("Ganesh", "Ganesh")
    }
}

class B: A() {
    override fun print():String {
        return "Ganesh"
        Log.i("Ganesh", "Ganesh")
    }
}