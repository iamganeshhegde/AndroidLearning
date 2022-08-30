package com.example.firstcomposeactivity.todoapp.ui.todo_list

import com.example.firstcomposeactivity.todoapp.data.ToDo

sealed class ToDOListEvent {

    data class onDeleteTodoClick(val toDo: ToDo) : ToDOListEvent()

    data class onDoneChange(val todo: ToDo, val isSOne: Boolean) : ToDOListEvent()

    object onUnDoDeleteClick : ToDOListEvent()

    data class onToDoCLick(val todo: ToDo) : ToDOListEvent()

    object onAddToDOCLick : ToDOListEvent()
}
