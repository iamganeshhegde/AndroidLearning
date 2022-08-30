package com.example.firstcomposeactivity.todoapp.ui.add_edittodo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeactivity.todoapp.data.ToDo
import com.example.firstcomposeactivity.todoapp.data.ToDoRepository
import com.example.firstcomposeactivity.todoapp.util.UiEventToDo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repo: ToDoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    var todo by mutableStateOf<ToDo?>(null)
        private set


    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set


    private val _uiEvents = Channel<UiEventToDo>()

    val uiEvent = _uiEvents.receiveAsFlow()


    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!

        if (todoId != -1) {
            viewModelScope.launch {
                repo.getToDOById(todoId)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""
                    this@AddEditViewModel.todo = todo
                }
            }
        }

    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditTodoEvent.OnDescriptionChange -> {

                description = event.description
            }
            is AddEditTodoEvent.onSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvents(
                            UiEventToDo.ShowSnakBar(
                                message = "The title can't be empty"
                            )
                        )
                        return@launch

                    }

                    repo.insertTodo(
                        ToDo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false,
                            id = todo?.id
                        )
                    )

                    sendUiEvents(UiEventToDo.PopBackStack)
                }


            }
        }
    }

    private fun sendUiEvents(event: UiEventToDo) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }


}