package com.example.firstcomposeactivity.crypto.presentation.coin_list.components

import com.example.firstcomposeactivity.crypto.domain.model.Coin

data class CoinListState(
    val isLoading:Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)
