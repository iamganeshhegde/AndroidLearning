package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenerationI(
    val redblue: RedBlue,
    val yellow: Yellow
)