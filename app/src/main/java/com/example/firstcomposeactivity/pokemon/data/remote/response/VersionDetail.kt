package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VersionDetail(
    val rarity: Int,
    val version: VersionX
)