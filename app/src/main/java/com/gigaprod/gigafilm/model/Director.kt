package com.gigaprod.gigafilm.model

import kotlinx.serialization.Serializable
@Serializable
data class Director(
    val id: Int,
    val name: String
)