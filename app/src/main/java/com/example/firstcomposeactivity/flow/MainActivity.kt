package com.example.firstcomposeactivity.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstcomposeactivity.ui.theme.FirstComposeActivityTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

        }
        setContent {
            FirstComposeActivityTheme {
                val viewModel = viewModel<MainViewModel>()
                val count = viewModel.stateFlow.collectAsState(initial = 0)




                LaunchedEffect(key1 = true) {
                    viewModel.sharedFlow.collect { number ->

                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Button(
                        onClick = {
                            viewModel.incrementCOunter()
                        }
                    ) {
                        Text(text = "Counter ${count.value}")
                    }
                }
            }
        }
    }
}
