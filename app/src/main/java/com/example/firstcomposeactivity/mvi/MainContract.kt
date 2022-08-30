package com.example.firstcomposeactivity.mvi

class MainContract {

    // Events that user performed
    sealed class Event : UiEvent {
        object OnRandomNumberClicked : Event()
        object OnShowToastClicked : Event()
    }

    // Ui View States
    data class State(
        val randomNumberState: RandomNumberState
    ) : UiState

    // View State that related to Random Number
    sealed class RandomNumberState {
        object Idle : RandomNumberState()
        object Loading : RandomNumberState()
        data class Success(val number : Int) : RandomNumberState()
    }

    // Side effects
    sealed class Effect : UiEffect {

        object ShowToast : Effect()

    }
}