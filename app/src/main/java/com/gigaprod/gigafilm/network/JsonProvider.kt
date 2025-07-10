package com.gigaprod.gigafilm.network
import Content
import Movie
import Tv
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.serializer

object JsonProvider {
    val json = Json {
        classDiscriminator = "content_type"
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            polymorphic(Content::class) {
                subclass(Movie::class, serializer<Movie>())
                subclass(Tv::class, serializer<Tv>())
            }
        }
    }
}
