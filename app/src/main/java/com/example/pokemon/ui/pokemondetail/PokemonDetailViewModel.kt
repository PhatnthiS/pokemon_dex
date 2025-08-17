package com.example.pokemon.ui.pokemondetail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.domain.usecase.GetPokemonDetailUseCase
import com.example.pokemon.domain.usecase.GetPokemonSpeciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val getPokemonSpeciesUseCase: GetPokemonSpeciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState

    fun loadPokemonDetail(id: String) {
        viewModelScope.launch {
            _uiState.value = PokemonDetailUiState.Loading

            try {
                val detail = getPokemonDetailUseCase(id)
                val specie = getPokemonSpeciesUseCase(id)

                _uiState.value = PokemonDetailUiState.Success(detail, specie)
            } catch (e: Exception) {

                showError(e.message)
            }
        }
    }

    private fun showError(message: String?) {
        _uiState.value = PokemonDetailUiState.Error(message ?: "Unknown error occurred")
    }
}
