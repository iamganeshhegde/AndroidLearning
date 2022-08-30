package com.example.firstcomposeactivity.pokemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firstcomposeactivity.misc.generic.GenericExamples
import com.example.firstcomposeactivity.pokemon.pokemondetails.PokemonDetailsScreen
import com.example.firstcomposeactivity.pokemon.pokemonlist.PokemonListScreen
import com.example.firstcomposeactivity.ui.theme.FirstComposeActivityTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class PokemonActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var genericMoshi = GenericExamples()
        genericMoshi.main()


        printStatements()

        setContent {
            FirstComposeActivityTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "pokemon_list_screen"
                ) {
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController = navController)
                    }

                    composable("pokemon_details_screen/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }

                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }


                        PokemonDetailsScreen(
                            dominantColor = dominantColor,
                            pokemonName = pokemonName?.lowercase() ?: "",
                            navController = navController
                        )
                    }

                }
            }

        }
    }

    private fun printStatements() {
//        val date = SimpleDateFormat("D", Locale.getDefault())

        val yourmilliseconds = System.currentTimeMillis()
//        val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm")
        val sdf = SimpleDateFormat("D").format(yourmilliseconds)
        Log.i("Ganesh", sdf)

    }
}



