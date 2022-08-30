package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenerationIv(
    val diamondpearl: DiamondPearl,
    val heartgoldsoulsilver: HeartgoldSoulsilver,
    val platinum: Platinum
)