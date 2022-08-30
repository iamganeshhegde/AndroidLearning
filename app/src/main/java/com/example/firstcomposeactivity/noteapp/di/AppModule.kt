package com.example.firstcomposeactivity.noteapp.di

import android.app.Application
import androidx.room.Room
import com.example.firstcomposeactivity.noteapp.feature_note.data.data_source.NoteDatabase
import com.example.firstcomposeactivity.noteapp.feature_note.data.data_source.NoteDatabase.Companion.DATABASE_NAME
import com.example.firstcomposeactivity.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.firstcomposeactivity.noteapp.feature_note.domain.repository.NoteRepository
import com.example.firstcomposeactivity.noteapp.feature_note.domain.usecase.AddNoteUseCase
import com.example.firstcomposeactivity.noteapp.feature_note.domain.usecase.DeleteNoteUseCase
import com.example.firstcomposeactivity.noteapp.feature_note.domain.usecase.GetNotesUseCase
import com.example.firstcomposeactivity.noteapp.feature_note.domain.usecase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db:NoteDatabase):NoteRepository {
        return NoteRepositoryImpl(db.noteDao)

    }

    @Provides
    @Singleton
    fun provideNoteUseCase(noteRepository:NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(noteRepository = noteRepository),
            deleteNoteUseCase = DeleteNoteUseCase(noteRepository = noteRepository),
            addNoteUseCase = AddNoteUseCase(noteRepository)
        )
    }

}