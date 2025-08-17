package com.example.pokemon.ui.pokemondetail


import com.example.pokemon.domain.model.PokemonDetail
import com.example.pokemon.domain.model.PokemonSpecies

sealed class PokemonDetailUiState {
    data object Loading : PokemonDetailUiState()
    data class Success(val detail: PokemonDetail, val specie: PokemonSpecies) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
}
