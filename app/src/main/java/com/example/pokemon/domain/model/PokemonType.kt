package com.example.pokemon.domain.model

data class PokemonTypeSlot(
    val type: PokemonType
)

data class PokemonType(
    val typeEnum: PokemonTypeEnum,
    val url: String
)