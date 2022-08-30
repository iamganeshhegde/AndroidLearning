package com.example.firstcomposeactivity.todoapp.ui.add_edittodo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firstcomposeactivity.todoapp.util.UiEventToDo
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEventToDo.PopBackStack -> {
                    onPopBackStack()

                }
                is UiEventToDo.ShowSnakBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        event.action
                    )
                }
                else -> {

                }
            }
        }
    }


    Scaffold(scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTodoEvent.onSaveTodoClick)
                }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "save"
                )
            }
        }) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = viewModel.title,
                onValueChange = { string ->
                    viewModel.onEvent(AddEditTodoEvent.OnTitleChange(string))
                },
                placeholder = { Text(text = "Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = viewModel.description,
                onValueChange = { string ->
                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(string))
                },
                placeholder = { Text(text = "Description") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5,
            )
        }
    }

}