package com.example.firstcomposeactivity.mockWebServer

import com.example.firstcomposeactivity.misc.moshi.jsonToObject
import com.example.firstcomposeactivity.misc.moshi.objectToJson
import com.example.firstcomposeactivity.pokemon.data.remote.PokeApi
import com.example.firstcomposeactivity.pokemon.data.remote.response.PokemonList
import com.example.firstcomposeactivity.pokemon.repository.PokemonRepository
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

abstract class BaseTest {

    private val server = MockWebServer()

    protected lateinit var repo: PokemonRepository


    @Before
    fun setup() {
        this.configureServer()
        this.configureRestApi()
    }

    private fun configureServer() {
        server.start(8080)
    }

    private fun configureRestApi() {
        repo = PokemonRepository(configureRetrofitApi())
    }

    private fun configureRetrofitApi(): PokeApi {
        return Retrofit.Builder().baseUrl(RemoteTestConstant.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(getOkHttpClient())
            .build()
            .create(PokeApi::class.java)
    }

    fun getResponse(path: String, responseCode: Int) {
        server.enqueue(MockResponse().setBody(getJson(path)).setResponseCode(responseCode))
    }

    private fun getOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.readTimeout(RemoteTestConstant.TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(RemoteTestConstant.TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(RemoteTestConstant.TIMEOUT, TimeUnit.SECONDS)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addNetworkInterceptor(loggingInterceptor)
        return okHttpClient.build()
    }

    fun <T> getExpectedResponse(path: String, response: Class<T>): T? {
        val moshi = Moshi.Builder().build()
//        moshi.jsonToObject<response::class.java>(getJson(path))
        val adapter = moshi.adapter(response).lenient()

        val start = System.nanoTime()
        val moshiResponse = adapter.fromJson(getJson(path = path))
        val end = System.nanoTime()
        println(" Moshi - ${end-start}")
        return moshiResponse

//        Moshi - 747417

//        val gson = Gson()
//        return gson.fromJson(getJson(path), response)
    }

    fun <T> getExpectedResponseGson(path: String, response: Class<T>): T? {
//        val moshi = Moshi.Builder().build()
//        moshi.jsonToObject<response::class.java>(getJson(path))
//        val adapter = moshi.adapter(response).lenient()
//        return adapter.fromJson(getJson(path = path))

        val gson = Gson()
        val start = System.nanoTime()
        val gsonResponse = gson.fromJson(getJson(path), response)
        val end = System.nanoTime()
        println(" Gson - ${end-start}")
        return gsonResponse
//        Gson - 8727458
    }

    private fun getJson(path: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        val content = reader.readText()
        reader.close()
        return content
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}