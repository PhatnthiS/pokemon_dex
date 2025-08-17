package com.example.pokemon.data.repository

import com.example.pokemon.data.remote.PokemonApiService
import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.model.PokemonDetail
import com.example.pokemon.domain.model.PokemonSpecies
import com.example.pokemon.domain.model.PokemonStat
import com.example.pokemon.domain.model.PokemonType
import com.example.pokemon.domain.model.PokemonTypeEnum
import com.example.pokemon.domain.model.PokemonTypeSlot
import com.example.pokemon.utils.getDisplayStat
import com.example.pokemon.utils.getIdFromUrl
import com.example.pokemon.utils.getSpriteBadgeUrl
import com.example.pokemon.utils.getSpriteUrl


class PokemonRepository(private val api: PokemonApiService) {

    suspend fun getPokemonList(): List<Pokemon> {
        val listResponse = api.getPokemonList()
        return listResponse.results.map { result ->
            val id = getIdFromUrl(result.url)
            Pokemon(
                id = id,
                name = result.name.replaceFirstChar { it.uppercase() },
                imageUrl = getSpriteUrl(id)
            )
        }
    }


    suspend fun getPokemonDetail(id: String): PokemonDetail {
        val detailResponse = api.getPokemonDetail(id)
        return PokemonDetail(
            name = detailResponse.name.replaceFirstChar { it.uppercase() },
            imageUrl = detailResponse.sprites.otherSprites.dreamWorld.frontDefault,
            height = detailResponse.height,
            weight = detailResponse.weight,
            types = detailResponse.types.map {
                PokemonTypeSlot(
                    type = PokemonType(
                        typeEnum = PokemonTypeEnum.fromName(it.type.name),
                        url = getSpriteBadgeUrl(getIdFromUrl(it.type.url))
                    )
                )
            },
            stats = detailResponse.stats.map {
                PokemonStat(name = getDisplayStat(it.stat.name), value = it.baseStat)
            },
            exp = detailResponse.exp
        )
    }

    suspend fun getPokemonSpecies(id: String): PokemonSpecies {
        val speciesResponse = api.getPokemonSpecies(id)
        return PokemonSpecies(flavorText = speciesResponse.flavorText.first().text, habitat = speciesResponse.habitat?.name)

    }
}
