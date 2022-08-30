package com.example.firstcomposeactivity.crypto.data.repository

import com.example.firstcomposeactivity.crypto.data.remote.CoinPaprikaApi
import com.example.firstcomposeactivity.crypto.data.remote.dto.CoinDetailsDto
import com.example.firstcomposeactivity.crypto.data.remote.dto.CoinDto
import com.example.firstcomposeactivity.crypto.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api:CoinPaprikaApi
): CoinRepository {
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailsDto {
        return api.getCoinById(coinId)
    }
}