package com.example.pokemon.data.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeSlot>,
    val stats: List<PokemonStats>,
    @SerializedName("base_experience") val exp: Int

)

data class Sprites(
    @SerializedName("front_default") val frontDefault: String,
    @SerializedName("other") val otherSprites: OtherSprites
)

data class OtherSprites(
    @SerializedName("dream_world") val dreamWorld: DreamWorld
)

data class DreamWorld(
    @SerializedName("front_default") val frontDefault: String?
)


data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)

data class PokemonType(
    val name: String,
    val url: String
)

data class PokemonStats(
    @SerializedName("base_stat") val baseStat: Int,
    val stat: Stat

)

data class Stat(
    val name: String

)