package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Form(
    val name: String,
    val url: String
)