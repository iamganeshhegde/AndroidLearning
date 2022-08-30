package com.example.firstcomposeactivity.flow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue

        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }


    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()


    private val _sharedFlow = MutableSharedFlow<Int>(replay = 5)
    val sharedFlow = _sharedFlow.asSharedFlow()


    init {
//        collectFlow()

        coroutineLearning()


        squareNumber(3)

        viewModelScope.launch {
            sharedFlow.collect {
                delay(2000L)
                println("First Flow the received number $it")
            }
        }

        viewModelScope.launch {
            sharedFlow.collect {
                delay(3000L)
                println("Second Flow the received number $it")
            }
        }

        squareNumber(3)

    }

    fun fibo(n: Int): Long {
        return if (n == 0) 0
        else if (n == 1) 1
        else fibo(n - 1) + fibo(n - 2)
    }

    private fun coroutineLearning() {












//GLobal scope
//        GlobalScope.launch {
//
//            val time = measureTimeMillis {

              /*  var answer1:String? = null
                var answer2:String? = null


                var job = launch {
                    answer1 = doNetworkCall()
                }

                var job2 =  launch {
                    answer2 = doNetworkCall2()
                }

                job.join()
                job2.join()

*/

//                val answer1 = async {
//                    doNetworkCall()
//                }
//
//                val answer2 = async {
//                    doNetworkCall2()
//                }




//                var answer1 = doNetworkCall()
//                var answer2 = doNetworkCall2()

//                Log.d("MainActivity", " Answer 1 ${answer1.await()}")
//                Log.d("MainActivity", " Answer 2 ${answer2.await()}")
//
//            }
//
//            Log.d("MainActivity", " time tok  $time")
//
//        }


//        val job = GlobalScope.launch(Dispatchers.Default) {

        /*repeat(5) {
            Log.d("MainActivity", " Coroutine still working")
            delay(1000)
        }*/


        /* Log.d("MainActivity", " Starting long running calc")

         withTimeout(2000L) {
             for (i in 30..40) {
                 if (isActive) {
                     Log.d("MainActivity", " Result for i = $i ${fibo(i)}")

                 }
             }
         }

         Log.d("MainActivity", " Ending long running calc")


     }*/

        /*runBlocking {
            delay(2000L)
            job.cancel()
            Log.d("MainActivity", " End, cancelled job")
        }*/


        // run blocking
        /*Log.d("MainActivity", " Befor runblocking")

        runBlocking { // same as thread.sleep


            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d("MainActivity", " Finished IO coroutine 1")

            }

            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d("MainActivity", " Finished IO coroutine 2")

            }

            Log.d("MainActivity", " start of runblocking")
            delay(5000L) //block ui
            Log.d("MainActivity", " end runblocking")

        }

        Log.d("MainActivity", " after runblocking")

*/
        /*GlobalScope.launch(Dispatchers.Main) {

            delay(3000L) // don't block ui
        }*/


        /*GlobalScope.launch(Dispatchers.IO) {
            val networkAnswer1 = doNetworkCall()
            Log.d("MainActivity", " io hello from ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                Log.d("MainActivity", " context change hello from ${Thread.currentThread().name}")
            }
        }

        Log.d("MainActivity", " outside hello from ${Thread.currentThread().name}")
*/
    }

    suspend fun doNetworkCall(): String {
        delay(3000L)
        return "This is answer 1"
    }

    suspend fun doNetworkCall2(): String {
        delay(3000L)
        return "This is answer 2"
    }

    fun squareNumber(number: Int) {
        viewModelScope.launch {
            _sharedFlow.emit(number * number)
        }

    }


    fun incrementCOunter() {
        _stateFlow.value += 1
    }


    private fun collectFlow() {

//        countDownFlow.onEach {
//
//        }.launchIn(viewModelScope)

        /*viewModelScope.launch {
            val count = countDownFlow
                .filter { time ->
                    time % 2 == 0
                }
                .map { time ->
                    time * time
                }
                .onEach { time ->
                    println("current $time")
                }
                .count {
                    it % 2 == 0
                }
            *//*.collect { time ->
            println("current time is $time")
        }*//*

            println("current count $count")
        }*/


//        viewModelScope.launch {
//            val reduceResult = countDownFlow
////                .reduce { accumulator, value ->
//                .fold(100) { accumulator, value ->
//                    accumulator + value
//                }
//
//            println("reducer is $reduceResult")
//
//
//        }


        /* val flow1 = flow {
             emit(1)
             delay(500L)
             emit(2)
         }
 */
        /*val flow1 = (1..5).asFlow()


        viewModelScope.launch {
            flow1.flatMapConcat { value ->
                flow {
                    emit(value + 1)
                    delay(500L)
                    emit(value + 2)
                }
            }.collect { value ->
                println("the value is $value")
            }
        }*/


        val flow = flow {
            delay(250L)
            emit("Appetizer")
            delay(1000L)
            emit("Main Dish")
            delay(100L)
            emit("Dessert")
        }

        viewModelScope.launch {
            flow.onEach {
                println("Flow $it is delivered")
            }
                .collectLatest {
                    println("Flow now eating $it")
                    delay(1500L)
                    println("Flow Finished Eating $it")
                }
        }
    }
}
