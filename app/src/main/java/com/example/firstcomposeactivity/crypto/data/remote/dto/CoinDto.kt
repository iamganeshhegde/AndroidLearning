package com.example.firstcomposeactivity.crypto.data.remote.dto

import com.example.firstcomposeactivity.crypto.domain.model.Coin

data class CoinDto(
    val id: String,
    val is_active: Boolean,
    val is_new: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)

fun CoinDto.toCoin(): Coin {
    return Coin(
        id,
        is_active,
        name,
        rank,
        symbol,
    )
}