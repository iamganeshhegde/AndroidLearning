package com.example.firstcomposeactivity.todoapp.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun insertTodo(toDo: ToDo)

    suspend fun deleteToDo(toDo: ToDo)

    suspend fun getToDOById(id: Int): ToDo?

    fun getToDo(): Flow<List<ToDo>>


}