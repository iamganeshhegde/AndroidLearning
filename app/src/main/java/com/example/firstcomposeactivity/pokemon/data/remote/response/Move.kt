package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)