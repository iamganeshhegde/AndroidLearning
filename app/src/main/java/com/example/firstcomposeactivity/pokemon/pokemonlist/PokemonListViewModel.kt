package com.example.firstcomposeactivity.pokemon.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.core.graphics.BitmapCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.firstcomposeactivity.pokemon.data.remote.response.Pokemon
import com.example.firstcomposeactivity.pokemon.data.remote.response.PokemonList
import com.example.firstcomposeactivity.pokemon.model.PokedexListEntry
import com.example.firstcomposeactivity.pokemon.repository.PokemonRepository
import com.example.firstcomposeactivity.pokemon.util.Constants.PAGE_SIZE
import com.example.firstcomposeactivity.pokemon.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {


    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    var endreached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokedexListEntry>()

    private var isSeachStarting = true

    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(
        query: String
    ) {
        val listToSearch = if (isSeachStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSeachStarting = true

                return@launch
            }

            val result = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }

            if (isSeachStarting) {
                cachedPokemonList = pokemonList.value
                isSeachStarting = false
            }

            pokemonList.value = result
            isSearching.value = true
        }

    }

    fun loadPokemonPaginated() {

        viewModelScope.launch {

            isLoading.value = true
            var result = repository.getPokemonList(limit = PAGE_SIZE, offSet = curPage * PAGE_SIZE)

            when (result) {
                is Resource.Success -> {
                    endreached.value = curPage * PAGE_SIZE >= result.data!!.count

                    val pokedexEntries = result.data!!.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }

                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"

                        PokedexListEntry(
                            entry.name.capitalize(Locale.ROOT),
                            url, number.toInt()
                        )
                    }

                    curPage++

                    loadError.value = ""
                    isLoading.value = false

                    pokemonList.value += pokedexEntries

                }

                is Resource.Error -> {

                    loadError.value = result.message.toString()
                    isLoading.value = false
                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {

        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }

    }
}