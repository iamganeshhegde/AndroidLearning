package com.example.firstcomposeactivity.crypto.presentation.coin_details

import com.example.firstcomposeactivity.crypto.domain.model.Coin
import com.example.firstcomposeactivity.crypto.domain.model.CoinDetails


data class CoinDetailState(
    val isLoading:Boolean = false,
    val coin: CoinDetails? = null,
    val error: String = ""
    )