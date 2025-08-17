package com.example.pokemon.data.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesResponse(
    @SerializedName("flavor_text_entries") val flavorText: List<FlavorText>,
    val habitat: Habitat?
)


data class FlavorText(
    @SerializedName("flavor_text") val text: String,
)

data class Habitat(
    val name: String,
)

