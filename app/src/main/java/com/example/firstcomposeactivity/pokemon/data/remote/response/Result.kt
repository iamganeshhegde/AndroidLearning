package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @SerializedName("name")
    @Json(name = "name")
    val name: String,
    @SerializedName("url")
    @Json(name = "url")
    val url: String
)