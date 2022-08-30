package com.example.firstcomposeactivity.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firstcomposeactivity.todoapp.ui.add_edittodo.AddEditTodoScreen
import com.example.firstcomposeactivity.todoapp.ui.todo_list.ToDoListScreen
import com.example.firstcomposeactivity.todoapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDOMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Routes.TODO_LIST
            ) {
                composable(route = Routes.TODO_LIST) {
                    ToDoListScreen(onNavigate = {
                        navController.navigate(it.route)
                    }
                    )
                }

                composable(route = Routes.ADD_TODO_LIST + "?todoId={todoId}",
                    arguments = listOf(
                        navArgument(name = "todoId") {
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                ) {
                    AddEditTodoScreen(onPopBackStack = {
                        navController.popBackStack()
                    })
                }
            }

        }
    }
}