package com.example.firstcomposeactivity.crypto.data.remote

import com.example.firstcomposeactivity.crypto.data.remote.dto.CoinDetailsDto
import com.example.firstcomposeactivity.crypto.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailsDto
}