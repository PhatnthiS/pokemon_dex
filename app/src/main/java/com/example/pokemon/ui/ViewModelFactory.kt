package com.example.pokemon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.domain.usecase.GetPokemonDetailUseCase
import com.example.pokemon.domain.usecase.GetPokemonSpeciesUseCase
import com.example.pokemon.domain.usecase.GetPokemonUseCase
import com.example.pokemon.ui.pokemondetail.PokemonDetailViewModel
import com.example.pokemon.ui.pokemonlist.PokemonListViewModel

class PokemonListViewModelFactory(
    private val useCase: GetPokemonUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            return PokemonListViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class PokemonDetailViewModelFactory(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val getPokemonSpeciesUseCase: GetPokemonSpeciesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            return PokemonDetailViewModel(getPokemonDetailUseCase, getPokemonSpeciesUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
