package com.example.firstcomposeactivity.noteapp.feature_note.presentation.notes.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun GenericExoPlayer(
    exoPlayer: SimpleExoPlayer?,
    modifier: Modifier = Modifier,
    playerResizeMode: Int = RESIZE_MODE_FIT,
    onUpdate: () -> Unit = {}
) {
    AndroidView(
        factory = {
            PlayerView(it).apply {
                useController = false
                setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                player = exoPlayer.also {
                    resizeMode = playerResizeMode
                }
            }
        }, modifier = modifier
    )
    {
        onUpdate()
    }
}