package com.example.firstcomposeactivity.noteapp.feature_note.domain.usecase

import androidx.compose.ui.text.toLowerCase
import com.example.firstcomposeactivity.noteapp.feature_note.domain.model.Note
import com.example.firstcomposeactivity.noteapp.feature_note.domain.repository.NoteRepository
import com.example.firstcomposeactivity.noteapp.feature_note.domain.util.NoteOrder
import com.example.firstcomposeactivity.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {

        return noteRepository.getNotes().map { notes ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Title -> notes.sortedBy { it.title.toLowerCase() }
                        is NoteOrder.Colour -> notes.sortedBy { it.colour }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.toLowerCase() }
                        is NoteOrder.Colour -> notes.sortedByDescending { it.colour }
                    }
                }
            }
        }
    }
}