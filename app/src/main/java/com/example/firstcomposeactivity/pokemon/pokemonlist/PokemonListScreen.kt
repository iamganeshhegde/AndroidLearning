package com.example.firstcomposeactivity.pokemon.pokemonlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.Disposable
import coil.request.ImageRequest
import com.example.firstcomposeactivity.pokemon.model.PokedexListEntry
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.delay

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    GifterBattleWinner()

    /*androidx.compose.material.Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {


            GifterBattleWinner()

            Spacer(modifier = Modifier.height(20.dp))

            *//*Image(
                painter = painterResource(id = R.drawable.ic_baseline_headset_24),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )*//*

            SearchBar(
                hint = "Search...", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchPokemonList(it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            PokemonList(navController = navController)


        }
    }*/
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text, onValueChange = {
                text = it
                onSearch.invoke(it)
            }, maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(Color.Black),
            modifier = modifier
                .fillMaxWidth()
                .shadow(5.dp, shape = CircleShape)
                .background(Color.White, CircleShape)
                .padding(vertical = 12.dp, horizontal = 20.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                }
        )

        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = modifier
                    .padding(
                        vertical = 12.dp, horizontal = 20.dp
                    )
            )
        }

    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by remember {
        viewModel.pokemonList
    }

    val endReached by remember {
        viewModel.endreached
    }

    val loadError by remember {
        viewModel.loadError
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    var isSearching by remember {
        viewModel.isSearching
    }


    LazyColumn(
        contentPadding = PaddingValues(
            16.dp
        )
    ) {

        val itemCount = if (pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        } else {
            pokemonList.size / 2 + 1
        }

        items(itemCount) {
            if (it >= itemCount - 1 && !endReached && !isLoading && !isSearching) {

                viewModel.loadPokemonPaginated()
            }
            PokedexRow(rowIndex = it, list = pokemonList, navController = navController)
        }

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.Green)
        }

        if (loadError.isNotEmpty()) {

            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }
        }
    }

}

@Composable
fun PokedexListEntry(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantaColor = MaterialTheme.colors.surface

    var dominantColor by remember {
        mutableStateOf(defaultDominantaColor)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantaColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_details_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
                )
            }
    ) {
        Column {
            Image(
                painter = rememberCoilPainter(
                    request = ImageRequest
                        .Builder(LocalContext.current)
                        .data(entry.imgUrl)
                        .target {
                            viewModel.calcDominantColor(drawable = it) { color ->
                                dominantColor = color
                            }
                        }.build()
                ),
                contentDescription = entry.pokemonName,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
            )

            Text(
                text = entry.pokemonName,
                fontFamily = FontFamily.Cursive,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun PokedexRow(
    rowIndex: Int,
    list: List<PokedexListEntry>,
    navController: NavController
) {

    Column {

        Row {

            com.example.firstcomposeactivity.pokemon.pokemonlist.PokedexListEntry(
                entry = list[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            if (list.size >= rowIndex * 2 + 2) {
                com.example.firstcomposeactivity.pokemon.pokemonlist.PokedexListEntry(
                    entry = list[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }

}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {

        Text(text = error, color = Color.Red, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onRetry() }, modifier = Modifier.align(
                CenterHorizontally
            )
        ) {
            Text(text = "Retry")
        }

    }

}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun GifterBattleWinner() {
    val data = "https://cdn.sharechat.com/38f4a7b1_1654002959625.webp"
    var enqueue: Disposable? = null


    val imageLoader = LocalImageLoader.current
    val context = LocalContext.current
    var show = remember {
        mutableStateOf(0)
    }


    fun preloadImages(data: String) {
        val imageRequest = ImageRequest.Builder(context)
            .data(data = data)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .build()
        enqueue = imageLoader.enqueue(imageRequest)
        println("imageLoader called")
    }

    DisposableEffect(key1 = true) {
        onDispose {
            enqueue?.dispose()
        }
    }

    LaunchedEffect(key1 = true) {
        preloadImages(data = data)
        println("preloadImages called")
    }

    LaunchedEffect(key1 = true) {
        delay(5000L)
        show.value = 1

        println("delay called")
    }

    if (show.value == 1) {
        println("Show called")
        PreImage(data = data)
    }
}


@ExperimentalCoilApi
@Composable
fun PreImage(data: String) {


    println("PreImage called")
    val painter = rememberImagePainter(data = data)
    val painterState = painter.state

    Image(painter = painter, contentDescription = "Gifter Battle Winner")

    println("PreImage before if condition called")
    println("PreImage before if condition called - painterstate - ${painterState}")
    if (painterState is ImagePainter.State.Success) {

        Image(
            painter = painter,
            contentDescription = "Gifter Battle Winner",
            modifier = Modifier.fillMaxWidth()
        )

        println("PreImage success called inside if")
        ConstraintLayout(
            modifier = Modifier
                .testTag("image_item")
//                .alpha(if (painterState is ImagePainter.State.Success) 1.0f else 0.0f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(painter = painter, contentDescription = "Gifter Battle Winner")

                println("PreImage success called inside if image displayed")

            }
        }
    } else {
        println("PreImage fail called inside else")
        println("PreImage fail called inside else - ${painterState}")
    }

}
