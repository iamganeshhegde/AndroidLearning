package com.example.firstcomposeactivity

import android.content.ContentValues.TAG
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import coil.transform.CircleCropTransformation
import com.example.firstcomposeactivity.databinding.ActivityMainBinding
import com.example.firstcomposeactivity.mvi.MainContract
import com.example.firstcomposeactivity.mvi.MainViewModel
import com.example.firstcomposeactivity.ui.theme.FirstComposeActivityTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.math.max

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    val TAG = "MainActivity"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        initObservers()
//        binding.generateNumber.setOnClickListener {
//            viewModel.setEvent(MainContract.Event.OnRandomNumberClicked)
//        }
//        binding.showToast.setOnClickListener {
//            viewModel.setEvent(MainContract.Event.OnShowToastClicked)
//        }
//        binding.secondActivity.setOnClickListener {
//            startActivity(Intent(this, SecondActivity::class.java))
//        }

        setContent {
            FirstComposeActivityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    TextViewCompose()
//                    CircularChecks()
//                    Conversation(SampleData.conversationSample)
//                    TutorialCompose()
//                    DisplayBox()
//                    Greeting("Android")
                }
            }
        }


        GlobalScope.launch(Dispatchers.IO) {
//            delay(1000L)
            Log.d(TAG, "onCreate: coroutine  hello from ${Thread.currentThread().name}")

            val networkCallAnswer = doNetworkCall()

            withContext(Dispatchers.Main) {

            }

            val networkCallAnswer2 = doNetworkCall2()

            Log.d(TAG, "onCreate: 1 $networkCallAnswer")

            Log.d(TAG, "onCreate: 2 $networkCallAnswer2")

        }

        Log.d(TAG, "onCreate:  hello from ${Thread.currentThread().name}")


        runBlocking {
            delay(1000)
        }

    }




    suspend fun doNetworkCall(): String {
        delay(3000L)
        return "This is the answer"
    }

    suspend fun doNetworkCall2(): String {
        delay(3000L)
        return "This is the second answer"
    }


    /**
     * Initialize Observers
     */
    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it.randomNumberState) {
                    is MainContract.RandomNumberState.Idle -> {
                        binding.progressBar.isVisible = false
                    }
                    is MainContract.RandomNumberState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is MainContract.RandomNumberState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.number.text = it.randomNumberState.number.toString()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is MainContract.Effect.ShowToast -> {
                        binding.progressBar.isVisible = false
                        showToast("Error, number is even")
                    }
                }
            }
        }
    }


    /**
     * Show simple toast message
     */
    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

}

@Composable
private fun TextViewCompose(text: String = "First") {
    var text by remember {
        mutableStateOf( text)
    }

    Text(text = text)


   /* GlobalScope.launch(Dispatchers.IO) {
//            delay(1000L)
        Log.d(TAG, "onCreate: coroutine  hello from ${Thread.currentThread().name}")

        val networkCallAnswer = doNetworkCall()

        withContext(Dispatchers.Main) {
            TextViewCompose(networkCallAnswer)
        }

        val networkCallAnswer2 = doNetworkCall2()

        Log.d(TAG, "onCreate: 1 $networkCallAnswer")

        Log.d(TAG, "onCreate: 2 $networkCallAnswer2")

    }*/

    Log.d(TAG, "onCreate:  hello from ${Thread.currentThread().name}")
}

@Composable
private fun CircularChecks() {
    CircularProgressIndicator()
}


@Composable
private fun Conversation(messages: List<Message>) {

    LazyColumn {
        items(messages) { message ->
            TutorialCompose(message = message)
        }
    }
}


@Composable
private fun TutorialCompose(message: Message) {
    Row(
        modifier = Modifier
//            .background(Color.Gray)
            .padding(8.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile pic",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
//                .background(Color.Red)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember {
            mutableStateOf(false)
        }

        val surfaceColour: Color by animateColorAsState(if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface)

        Column(modifier = Modifier.clickable {
            isExpanded = !isExpanded
        }) {
            Text(
                message.title,
//                modifier = Modifier
//                    .background(Color.DarkGray),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.material.Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 5.dp,
                color = surfaceColour,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    message.body,
//                    modifier = Modifier.background(Color.Blue),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }

        }

    }

}

@Composable
fun DisplayBox() {

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "This is drawn first", modifier = Modifier.align(Alignment.TopCenter))


        Box(
            Modifier
                .align(Alignment.TopCenter)
                .height(100.dp)
                .width(50.dp)
                .background(Color.Blue)
        )

        Text(
            text = "This drawn at last",
            modifier = Modifier.align(Alignment.Center),
            color = Color.Green
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.BottomCenter)
                .clickable { }, elevation = 10.dp
        ) {

            Column(modifier = Modifier.padding(15.dp)) {
                Text(buildAnnotatedString {
                    append("Welcome to")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W900,
                            color = Color(0xFF4552B8)
                        )
                    ) {
                        append("Jetpack compose playground")
                    }
                })

                Text(buildAnnotatedString {
                    append("You are in the")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append("Card")
                    }
                    append(" Section")
                })
            }

        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .matchParentSize()
                .padding(12.dp),
            onClick = {

            }) {
            Text(text = "+")
        }

    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview() {
    FirstComposeActivityTheme {

        Conversation(messages = SampleData.conversationSample)
//        TutorialCompose()
//        Greeting("Android")
//        TutorialCompose()
//        DisplayBox()
    }

}

@Composable
fun CoffeeTypeItem(type:CoffeeType) {

    Row(modifier = Modifier.padding(16.dp)) {
        Spacer(Modifier.padding(16.dp))
        Text(text = type.name, style = typography.body1,
        modifier = Modifier.padding())
    }

}


data class CoffeeType(
    @DrawableRes
    val image: Int,
    val name: String,
    val count: Int = 0
)

/*
@Composable
fun UserExitScreen(
    modifier: Modifier = Modifier,
    preGoLiveViewModel: ViewModel,
    followAndLeave: () -> Unit,
    leave: () -> Unit,
    cancel: () -> Unit
) {
//    val hostMeta = preGoLiveViewModel.host

    Column(
        modifier = modifier
    ) {
        Image(
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                data = preGoLiveViewModel.host.memberThumb,
                builder = {
                    transformations(CircleCropTransformation())
                }),
            contentDescription = "Profile",
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(56.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
        )

        Text(
            text = preGoLiveViewModel.getExitHeader(),
            textAlign = TextAlign.Center,
            color = AppTheme.colors.onSurface,
            style = AppTheme.typography.title,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = preGoLiveViewModel.getExitSubHeader(),
            color = AppTheme.colors.disable,
            style = AppTheme.typography.body
        )

        if (hostMeta.following) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = AppTheme.shapes.round8,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.onSurface,
                    contentColor = AppTheme.colors.surface
                ),
                onClick = leave
            ) {
                Text(
                    text = stringResource(id = R.string.leave),
                    style = AppTheme.typography.bodyBold
                )
            }

            Text(
                text = stringResource(id = R.string.cancel),
                style = AppTheme.typography.bodyBold,
                color = AppTheme.colors.disable,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = cancel)
            )
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = AppTheme.shapes.round8,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.onSurface,
                    contentColor = AppTheme.colors.surface
                ),
                onClick = followAndLeave
            ) {
                Text(
                    text = stringResource(id = R.string.follow_and_leave),
                    style = AppTheme.typography.bodyBold
                )
            }

            Text(
                text = stringResource(id = R.string.leave),
                style = AppTheme.typography.bodyBold,
                color = AppTheme.colors.disable,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = leave)
            )
        }
    }
}*/
