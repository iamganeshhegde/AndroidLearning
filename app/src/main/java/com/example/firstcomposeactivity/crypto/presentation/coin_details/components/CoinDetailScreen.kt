package com.example.firstcomposeactivity.crypto.presentation.coin_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firstcomposeactivity.crypto.data.remote.dto.TeamMember
import com.example.firstcomposeactivity.crypto.presentation.Screen
import com.example.firstcomposeactivity.crypto.presentation.coin_details.CoinDetailsViewModel
import com.example.firstcomposeactivity.crypto.presentation.coin_list.CoinListViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun CoinDetailsScreen(
    viewModel: CoinDetailsViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {

        state.coin?.let { coin ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${coin.rank} ${coin.name} (${coin.symbol})",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(8f)
                        )

                        Text(
                            text = if (coin.isActive) "active" else "inactive",
                            color = if (coin.isActive) Color.Green else Color.Red,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier
                                .align(CenterVertically)
                                .weight(2f)
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = coin.description?:"",
                        style = MaterialTheme.typography.body2,
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Tags",
                        style = MaterialTheme.typography.h5,
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    FlowRow(
                        mainAxisSpacing = 10.dp,
                        crossAxisSpacing = 10.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        coin.tags?.forEach { tag ->
                            CoinTag(tag = tag)
                        }

                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Team members",
                        style = MaterialTheme.typography.h5,
                    )

                    Spacer(modifier = Modifier.height(15.dp))


                }

                coin.team?.let {
                    items(it) { teamMember ->

                        TeamListItem(
                            teamMember = teamMember, modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                        Divider()
                    }
                }

            }

        }


        if (state.error.isNotBlank()) {

            Text(
                state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }

}
