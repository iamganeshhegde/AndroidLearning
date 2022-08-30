package com.example.firstcomposeactivity.pokemon.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Versions(
    val generationi: GenerationI,
    val generationii: GenerationIi,
    val generationiii: GenerationIii,
    val generationiv: GenerationIv,
    val generationv: GenerationV,
    val generationvi: GenerationVi,
    val generationvii: GenerationVii,
    val generationviii: GenerationViii
)