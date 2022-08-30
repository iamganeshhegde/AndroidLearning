package com.example.firstcomposeactivity.todoapp.data

import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(
    private val toDoDao: ToDoDao
) : ToDoRepository {

    override suspend fun insertTodo(toDo: ToDo) {
        toDoDao.insertTodo(toDo = toDo)
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        toDoDao.deleteToDo(toDo)
    }

    override suspend fun getToDOById(id: Int): ToDo? {
        return toDoDao.getToDOById(id)
    }

    override fun getToDo(): Flow<List<ToDo>> {
        return toDoDao.getToDo()
    }
}