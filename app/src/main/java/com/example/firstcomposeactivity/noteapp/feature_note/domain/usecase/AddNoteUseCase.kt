package com.example.firstcomposeactivity.noteapp.feature_note.domain.usecase

import com.example.firstcomposeactivity.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.firstcomposeactivity.noteapp.feature_note.domain.model.Note
import com.example.firstcomposeactivity.noteapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the node cant be empty")
        }

        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the node cant be empty")
        }

        repository.insertNote(note = note)
    }
}