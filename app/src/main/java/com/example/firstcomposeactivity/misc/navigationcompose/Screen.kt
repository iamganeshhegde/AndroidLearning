package com.example.firstcomposeactivity.misc.navigationcompose

sealed class Screen(val route:String) {

    object MainScreen : Screen(route = "main_screen")
    object DetailsScreen : Screen(route = "detail_screen")


    fun withArgs(vararg arg:String):String {
        return buildString {
            append(route)

            arg.forEach { arg ->
                append("/$arg")
            }

        }
    }

}