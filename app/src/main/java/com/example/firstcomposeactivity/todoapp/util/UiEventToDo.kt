package com.example.firstcomposeactivity.todoapp.util

sealed class UiEventToDo {

    object PopBackStack : UiEventToDo()

    data class Navigate(val route: String) : UiEventToDo()

    data class ShowSnakBar(
        val message: String,
        val action: String? = null
    ): UiEventToDo()

}
