package com.gigaprod.gigafilm.model

import kotlinx.serialization.Serializable
@Serializable
data class Actor(
    val id: Int,
    val name: String,
    val character: String
)
