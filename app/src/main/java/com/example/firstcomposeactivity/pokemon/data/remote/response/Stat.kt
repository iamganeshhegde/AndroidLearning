package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatX
)