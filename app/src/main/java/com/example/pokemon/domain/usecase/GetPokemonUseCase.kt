package com.example.pokemon.domain.usecase

import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.domain.model.Pokemon


class GetPokemonUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(): List<Pokemon> {
        return repository.getPokemonList()
    }
}
