package com.example.firstcomposeactivity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen() {
    val listState = rememberScrollState()

    Box(
        modifier = Modifier
            .background(Color(0xFF474C68))
            .fillMaxSize()
    ) {
        Column {
            GreetingSection()
            ChipSection(listOfChips = listOf("Sweet Sleep", "Insomnia", "Depression"))
            CurrentMedidation()

            FeatureSection(
                features = listOf(
                    Feature(
                        title = "Sleep meditation",
                        R.drawable.ic_baseline_headset_24,
                        Color(0xFF7D4686),
                        Color(0xFF682274),
                        Color(0xFF4A0755)
                    ),
                    Feature(
                        title = "Tips for sleeping",
                        R.drawable.ic_baseline_videocam_24,
                        Color(0xFF727BAC),
                        Color(0xFF465399),
                        Color(0xFF18225F)
                    ),
                    Feature(
                        title = "Night island",
                        R.drawable.ic_baseline_headset_24,
                        Color(0xFF648FA3),
                        Color(0xFF316379),
                        Color(0xFF062431)
                    ),
                    Feature(
                        title = "Calming sounds",
                        R.drawable.ic_baseline_headset_24,
                        Color(0xFFA27567),
                        Color(0xFF663626),
                        Color(0xFF3D180C)
                    )
                )
            )


        }
        BottomMenu(
            item = listOf(
                BottomMenuContent("Home", R.drawable.ic_baseline_home_24),
                BottomMenuContent("Meditate", R.drawable.ic_baseline_bubble_chart_24),
                BottomMenuContent("Sleep", R.drawable.ic_baseline_mood_24),
                BottomMenuContent("Music", R.drawable.ic_baseline_music_note_24),
                BottomMenuContent("Profile", R.drawable.ic_baseline_person_24)
            ), modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}

@Composable
fun BottomMenu(

    item: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = Color.Blue,
    activeTextColor: Color = Color.White,
    inActiveTextColor: Color = Color(0xFF538BB8),
    initialItemSelectedIndex: Int = 0
) {

    var selectedItemIndex by remember {
        mutableStateOf(initialItemSelectedIndex)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF223B4E))
            .padding(15.dp)
    ) {

        item.forEachIndexed { index, bottomMenuContent ->
            BottomMenuItem(
                item = bottomMenuContent,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inActiveTextColor = inActiveTextColor
            ) {
                selectedItemIndex = index

            }
        }
    }

}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = Color.Blue,
    activeTextColor: Color = Color.White,
    inActiveTextColor: Color = Color(0xFF538BB8),
    onItemCLicked: () -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onItemCLicked()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isSelected) activeHighlightColor else Color.Transparent
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {

            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inActiveTextColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = item.title, color = if (isSelected) activeTextColor else inActiveTextColor
        )
    }

}

@Composable
fun GreetingSection(
    name: String = "Ganesh"
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "Good Morning, $name", style = TextStyle(fontSize = 30.sp))
            Text(text = "We wish you have a good day", style = TextStyle(fontSize = 20.sp))
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )


    }
}

@Composable
fun ChipSection(listOfChips: List<String>) {

    var selectedChip by remember {
        mutableStateOf(0)
    }

    LazyRow {
        items(listOfChips.size) {
            Box(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable { selectedChip = it }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedChip == it) Color.Blue
                        else Color.Black
                    )
                    .padding(15.dp)) {

                Text(listOfChips[it], color = Color(0xFFffffDD))
            }
        }
    }

}

@Composable
fun CurrentMedidation(
    color: Color = Color(0xFFE4BCE0)
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(15.dp))
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
            .background(color,shape = RoundedCornerShape(10.dp))
    ) {

        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "Daily Thought", style = TextStyle(fontSize = 30.sp))
            Text(
                text = "Meditation 3-10 min",
                style = TextStyle(fontSize = 20.sp),
                color = Color(0xFFFFFFEE)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Blue)
                .padding(10.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                contentDescription = "Player",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )

        }

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeatureSection(features: List<Feature>) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Features",
            style = MaterialTheme.typography.subtitle2
        )

    }
}


@Composable
fun FeatureItem(
    feature: Feature
) {

    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        //medium coloured path
        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())


        val mediumCOlouredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)

            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)

            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }


        //Light coloured path
        val lightColoredPoint1 = Offset(0f, height * 0.35f)
        val lightColoredPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightColoredPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightColoredPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightColoredPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)


        val lightCOlouredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)

            standardQuadFromTo(lightColoredPoint1, lightColoredPoint2)
            standardQuadFromTo(lightColoredPoint2, lightColoredPoint3)
            standardQuadFromTo(lightColoredPoint3, lightColoredPoint4)
            standardQuadFromTo(lightColoredPoint4, lightColoredPoint5)

            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }


        Canvas(modifier = Modifier.fillMaxSize()) {
            drawPath(
                path = mediumCOlouredPath,
                color = feature.mediumColor,

                )

            drawPath(
                path = lightCOlouredPath,
                color = feature.lightColor,

                )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = feature.title, style = MaterialTheme.typography.body1, lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
            )

            Text(
                text = "Start",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {

                    }
                    .align(Alignment.BottomEnd)
                    .background(Color(0xFF08145A))
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }

    }

}