package com.example.firstcomposeactivity.crypto.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeactivity.crypto.common.Resource
import com.example.firstcomposeactivity.crypto.domain.usecase.getcoins.GetCoinsUseUse
import com.example.firstcomposeactivity.crypto.presentation.coin_list.components.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseUse: GetCoinsUseUse
) : ViewModel() {

    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state


    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseUse().onEach { result ->

            when (result) {
                is Resource.Success -> {

                    _state.value = CoinListState(
                        coins = result.data ?: emptyList()
                    )

                }
                is Resource.Error -> {

                    _state.value = CoinListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}