package com.example.firstcomposeactivity.todoapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.firstcomposeactivity.todoapp.data.ToDoDatabase
import com.example.firstcomposeactivity.todoapp.data.ToDoRepository
import com.example.firstcomposeactivity.todoapp.data.ToDoRepositoryImpl
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
    fun provideTodoDatabse(app: Application): ToDoDatabase {
        return Room.databaseBuilder(
            app,
            ToDoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDORepository(
        toDoDatabase:ToDoDatabase
    ): ToDoRepository {
        return ToDoRepositoryImpl(toDoDatabase.dao)
    }

}