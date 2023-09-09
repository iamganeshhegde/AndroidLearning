package com.example.firstcomposeactivity.something

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty


abstract class CancellableContainerViewModel<STATE : Any, SIDE_EFFECT : Any>(
    protected val savedStateHandle: SavedStateHandle
) : ViewModel(), ContainerHost<STATE, SIDE_EFFECT> {

    companion object {
        const val TAG = "CustomScopeBaseViewModel"
    }

    val exceptionHandler = CoroutineExceptionHandler{a,b ->}

    @Suppress("UNCHECKED_CAST")
    open fun initialState(): STATE = DefaultState as STATE

    protected var containerScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private var scopeContainer: Container<STATE, SIDE_EFFECT>? = null

    override val container: Container<STATE, SIDE_EFFECT>
        get() {
            return if (containerScope.isActive) {
                if (scopeContainer == null) {
                    scopeContainer = initializeContainer()
                }
                scopeContainer as Container<STATE, SIDE_EFFECT>
            } else {
                containerScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
                scopeContainer = initializeContainer()
                scopeContainer as Container<STATE, SIDE_EFFECT>
            }
        }

    fun initializeContainer() = containerScope.container<STATE, SIDE_EFFECT>(
        initialState = initialState(),
        settings = Container.Settings(
            intentDispatcher = Dispatchers.Default, exceptionHandler = exceptionHandler
        )
    ) {
        initData()
    }

    fun stateFlow(): StateFlow<STATE> = container.stateFlow

    fun sideFlow(): Flow<SIDE_EFFECT> = container.sideEffectFlow

    open fun initData() = intent {}

    fun cancelContainerScope() {
        containerScope.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        containerScope.cancel()
        Log.d("Ganesh", "$this ${hashCode()} onCleared")
    }

    fun <T> Flow<T>.launchIn(): Job = viewModelScope.launch(exceptionHandler) {
        collect()
    }
}


object DefaultState

object DefaultSideEffect
