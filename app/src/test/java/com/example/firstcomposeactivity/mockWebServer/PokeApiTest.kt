package com.example.firstcomposeactivity.mockWebServer

import com.example.firstcomposeactivity.pokemon.data.remote.response.PokemonList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.net.HttpURLConnection


class PokeApiTest : BaseTest() {

    @Test
    fun getPokeApi() {

        runBlocking {

            val start = System.nanoTime()

            val expectedResponse = getExpectedResponse("pokelist.json", PokemonList::class.java)

            getResponse("pokelist.json", HttpURLConnection.HTTP_OK)

            val result = repo.getPokemonList(10, 10)

            Assert.assertEquals(expectedResponse?.count ?: 0, result.data?.count ?: 0)
            val end = System.nanoTime()

            println(end - start)

        }

    }

    @Test
    fun getPokeApiGson() {

        runBlocking {

            val start = System.nanoTime()

            val expectedResponse = getExpectedResponseGson("pokelist.json", PokemonList::class.java)

            getResponse("pokelist.json", HttpURLConnection.HTTP_OK)

            val result = repo.getPokemonList(10, 10)

            Assert.assertEquals(expectedResponse?.count ?: 0, result.data?.count ?: 0)
            val end = System.nanoTime()

            println(end - start)

        }

    }

}
//8727458
//747417