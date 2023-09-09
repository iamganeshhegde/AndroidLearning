package com.example.firstcomposeactivity.something

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.syntax.simple.intent
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(savedStateHandle: SavedStateHandle):CancellableContainerViewModel<FirstState,FirstSideEffect>(savedStateHandle) {

    init {
        Log.d("Ganesh", "First view model init")
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("Ganesh", "First view model cleared")
    }

    fun callingfromActivity() = intent{
        for (i in 0.. 5) {
            delay(100)
            Log.d("Ganesh", "Logging i from first viewmodel ${i}")
        }
    }


}


@HiltViewModel
class SecondViewModel @Inject constructor(savedStateHandle: SavedStateHandle):CancellableContainerViewModel<SecondState,SecondSideEffect>(savedStateHandle) {

    init {
        Log.d("Ganesh", "Second view model init")
//        doSomething()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("Ganesh", "Second view model cleared")
    }

    fun doSomething() = intent {
        for (j in 0.. 500) {
            delay(100)
            Log.d("Ganesh", "Logging j from second ${j}")
        }
    }

}

object FirstState
object SecondState



object FirstSideEffect
object SecondSideEffect




