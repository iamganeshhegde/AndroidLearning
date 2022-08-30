package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)