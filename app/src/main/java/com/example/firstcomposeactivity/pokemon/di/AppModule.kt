package com.example.firstcomposeactivity.pokemon.di

import com.example.firstcomposeactivity.pokemon.data.remote.PokeApi
import com.example.firstcomposeactivity.pokemon.repository.PokemonRepository
import com.example.firstcomposeactivity.pokemon.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit
            .Builder()
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Singleton
    @Provides
    fun providePokemonrepository(
        api: PokeApi
    ) = PokemonRepository(api = api)
}