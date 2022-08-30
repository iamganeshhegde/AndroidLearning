package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonList(
    @SerializedName("count")
    @Json(name = "count")
    val count: Int,
    @SerializedName("next")
    @Json(name = "next")
    val next: String,
    @SerializedName("previous")
    @Json(name = "previous")
    val previous: Any?,
    @SerializedName("results")
    @Json(name = "results")
    val results: List<Result>
)