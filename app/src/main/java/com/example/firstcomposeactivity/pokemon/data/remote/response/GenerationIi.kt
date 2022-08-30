package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenerationIi(
    val crystal: Crystal,
    val gold: Gold,
    val silver: Silver
)