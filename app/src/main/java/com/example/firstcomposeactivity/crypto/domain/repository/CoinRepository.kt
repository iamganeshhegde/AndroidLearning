package com.example.firstcomposeactivity.crypto.domain.repository

import com.example.firstcomposeactivity.crypto.data.remote.dto.CoinDetailsDto
import com.example.firstcomposeactivity.crypto.data.remote.dto.CoinDto

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetailsDto
}