package com.example.firstcomposeactivity.crypto.presentation.coin_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeactivity.crypto.common.Constant.COIN_ID
import com.example.firstcomposeactivity.crypto.common.Resource
import com.example.firstcomposeactivity.crypto.domain.usecase.getcoindetails.GetCoinDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state


    init {
        savedStateHandle.get<String>(COIN_ID)?.let { coinId ->
            getCoinDetails(coinId = coinId)
        }
    }

    private fun getCoinDetails(coinId: String) {
        getCoinDetailsUseCase(coinId).onEach { result ->

            when (result) {
                is Resource.Success -> {

                    Log.i("Coin", "coin id - $coinId")
                    Log.i("Coin", "result  - ${result.data}")
                    Log.i("Coin", "result tags size - ${result.data?.tags?.size}")
                    _state.value = CoinDetailState(
                        coin = result.data
                    )

                }
                is Resource.Error -> {

                    _state.value = CoinDetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CoinDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}
