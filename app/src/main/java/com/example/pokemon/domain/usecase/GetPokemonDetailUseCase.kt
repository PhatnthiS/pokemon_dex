package com.example.pokemon.domain.usecase

import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.domain.model.PokemonDetail

class GetPokemonDetailUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: String): PokemonDetail {
        return repository.getPokemonDetail(id)
    }
}