package com.example.firstcomposeactivity.pokemon.pokemondetails

import androidx.lifecycle.ViewModel
import com.example.firstcomposeactivity.pokemon.data.remote.response.Pokemon
import com.example.firstcomposeactivity.pokemon.repository.PokemonRepository
import com.example.firstcomposeactivity.pokemon.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(
        pokemonName: String
    ): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }
}

