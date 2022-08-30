package com.example.firstcomposeactivity.todoapp.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeactivity.todoapp.data.ToDo
import com.example.firstcomposeactivity.todoapp.data.ToDoRepository
import com.example.firstcomposeactivity.todoapp.util.Routes
import com.example.firstcomposeactivity.todoapp.util.UiEventToDo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDOListViewModel @Inject constructor(
    val repository: ToDoRepository
) : ViewModel() {

    val todos = repository.getToDo()

    private val _uiEvents = Channel<UiEventToDo>()

    val uiEvent = _uiEvents.receiveAsFlow()

    private var deletedTodo: ToDo? = null

    fun onEvent(event: ToDOListEvent) {

        when (event) {
            is ToDOListEvent.onDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedTodo = event.toDo
                    repository.deleteToDo(event.toDo)
                    sendUiEvents(UiEventToDo.ShowSnakBar(message = "Todo deleted", action = "Undo"))
                }
            }

            is ToDOListEvent.onAddToDOCLick -> {

                sendUiEvents(UiEventToDo.Navigate(Routes.ADD_TODO_LIST))

            }
            is ToDOListEvent.onDoneChange -> {

                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isSOne
                        )
                    )
                }

            }
            is ToDOListEvent.onToDoCLick -> {

                sendUiEvents(UiEventToDo.Navigate(Routes.ADD_TODO_LIST + "?todoId=${event.todo.id}"))
            }
            is ToDOListEvent.onUnDoDeleteClick -> {
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
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