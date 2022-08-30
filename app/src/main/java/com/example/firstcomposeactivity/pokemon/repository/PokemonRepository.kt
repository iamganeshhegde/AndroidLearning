package com.example.firstcomposeactivity.pokemon.repository

import com.example.firstcomposeactivity.pokemon.data.remote.PokeApi
import com.example.firstcomposeactivity.pokemon.data.remote.response.Pokemon
import com.example.firstcomposeactivity.pokemon.data.remote.response.PokemonList
import com.example.firstcomposeactivity.pokemon.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {

    suspend fun getPokemonList(limit: Int, offSet: Int): Resource<PokemonList> {
        val response = try {

            api.getPokemonList(limit = limit, offSet)
        } catch (e: Exception) {
            return Resource.Error(message = "An unknown error occurred -  ${e.toString()}")
        }

        return Resource.Success(response)
    }


    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {

            api.getPokemonInfo(name = pokemonName)
        } catch (e: Exception) {
            return Resource.Error(message = "An unknown error occurred - ${e.toString()}")
        }

        return Resource.Success(response)
    }

}