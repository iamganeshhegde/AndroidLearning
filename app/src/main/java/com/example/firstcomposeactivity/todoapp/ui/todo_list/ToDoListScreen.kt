package com.example.firstcomposeactivity.todoapp.ui.todo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firstcomposeactivity.todoapp.util.UiEventToDo
import kotlinx.coroutines.flow.collect

@Composable
fun ToDoListScreen(
    onNavigate: (UiEventToDo.Navigate) -> Unit,
    viewModel: ToDOListViewModel = hiltViewModel()
) {

    val todos = viewModel.todos.collectAsState(initial = emptyList())

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->

            when (event) {
                is UiEventToDo.ShowSnakBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action

                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ToDOListEvent.onUnDoDeleteClick)
                    }

                }
                is UiEventToDo.Navigate -> {
                    onNavigate(event)
                }
            }
        }
    }


    Scaffold(scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(ToDOListEvent.onAddToDOCLick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add"
                )
            }
        }) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(todos.value) { todo ->
                ToDoItem(
                    todo = todo, onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(ToDOListEvent.onToDoCLick(todo))
                        }
                        .padding(16.dp)

                )
            }
        }

    }


}