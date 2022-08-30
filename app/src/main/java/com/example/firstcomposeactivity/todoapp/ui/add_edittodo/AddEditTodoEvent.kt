package com.example.firstcomposeactivity.todoapp.ui.add_edittodo

import android.content.res.AssetFileDescriptor

sealed class AddEditTodoEvent {

    data class OnTitleChange(val title: String) : AddEditTodoEvent()
    data class OnDescriptionChange(val description: String) : AddEditTodoEvent()
    object onSaveTodoClick : AddEditTodoEvent()
}