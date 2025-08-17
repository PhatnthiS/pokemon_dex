package com.example.pokemon.ui.pokemonlist

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.model.PokemonDetail
import com.example.pokemon.domain.usecase.GetPokemonUseCase
import com.example.pokemon.ui.pokemondetail.PokemonDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonUseCase: GetPokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            _uiState.value = PokemonListUiState.Loading
            try {

                val pokemonList = getPokemonUseCase()
                _uiState.value = PokemonListUiState.Success(
                    pokemon = pokemonList,
                    featurePokemon = pokemonList.shuffled().take((5..10).random())
                )
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        val currentState = _uiState.value
        if (currentState is PokemonListUiState.Success) {
            _uiState.value = currentState.copy(searchQuery = newQuery)
        }
    }
}
