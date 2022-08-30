package com.example.firstcomposeactivity.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTodo(toDo: ToDo)

    @Delete
    suspend fun deleteToDo(toDo: ToDo)

    @Query("SELECT * FROM todo WHERE id=:id")
    suspend fun getToDOById(id: Int): ToDo?

    @Query("SELECT * FROM todo")
    fun getToDo(): Flow<List<ToDo>>

}