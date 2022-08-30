package com.example.firstcomposeactivity.noteapp.feature_note.domain.util

import com.example.firstcomposeactivity.noteapp.feature_note.domain.model.Note

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Colour(orderType: OrderType) : NoteOrder(orderType)

    fun copy(orderType: OrderType):NoteOrder {
        return when(this) {
            is Title -> Title(orderType = orderType)
            is Date -> Date(orderType = orderType)
            is Colour -> Colour(orderType = orderType)
        }
    }
}
