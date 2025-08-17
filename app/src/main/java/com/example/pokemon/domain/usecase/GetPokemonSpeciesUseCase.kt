package com.example.pokemon.domain.usecase

import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.domain.model.PokemonDetail
import com.example.pokemon.domain.model.PokemonSpecies

class GetPokemonSpeciesUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: String): PokemonSpecies {
        return repository.getPokemonSpecies(id)
    }
}