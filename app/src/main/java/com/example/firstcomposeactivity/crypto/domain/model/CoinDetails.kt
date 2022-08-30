package com.example.firstcomposeactivity.crypto.domain.model

import com.example.firstcomposeactivity.crypto.data.remote.dto.Links
import com.example.firstcomposeactivity.crypto.data.remote.dto.LinksExtended
import com.example.firstcomposeactivity.crypto.data.remote.dto.Tag
import com.example.firstcomposeactivity.crypto.data.remote.dto.TeamMember
import com.example.firstcomposeactivity.crypto.data.remote.dto.Whitepaper
import com.google.gson.annotations.SerializedName

data class CoinDetails(
    val description: String?,
    val coinId: String,
    val isActive: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val tags: List<String>?,
    val team: List<TeamMember>?
)