package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenerationIii(
    val emerald: Emerald,
    val fireredleafgreen: FireredLeafgreen,
    val rubysapphire: RubySapphire
)