package com.example.pokemon.ui.pokemonlist


import com.example.pokemon.domain.model.Pokemon

sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data class Success(
        val pokemon: List<Pokemon>,
        val featurePokemon: List<Pokemon>,
        val searchQuery: String = ""
    ) : PokemonListUiState() {
        val filteredList: List<Pokemon>
            get() = if (searchQuery.isBlank()) pokemon
            else pokemon.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }
    data class Error(val message: String) : PokemonListUiState()
}

