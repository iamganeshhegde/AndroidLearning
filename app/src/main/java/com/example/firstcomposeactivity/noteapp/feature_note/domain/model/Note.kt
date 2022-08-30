package com.example.firstcomposeactivity.noteapp.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.firstcomposeactivity.pokemon.data.remote.response.RedBlue
import java.lang.Exception

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val colour: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColours = listOf(
            Color.Red,
            Color.Green,
            Color.Cyan,
            Color.Blue,
            Color.Magenta
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)
