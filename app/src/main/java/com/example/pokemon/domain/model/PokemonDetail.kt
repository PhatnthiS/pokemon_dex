package com.example.pokemon.domain.model

data class PokemonDetail(
    val name: String,
    val imageUrl: String?,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeSlot>,
    val stats: List<PokemonStat>,
    val exp: Int,
)