package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Other(
    val dream_world: DreamWorld,
    val home: Home,
    val officialartwork: OfficialArtwork
)