package com.example.pokemon.data.remote

import com.example.pokemon.data.remote.response.PokemonDetailResponse
import com.example.pokemon.data.remote.response.PokemonListResponse
import com.example.pokemon.data.remote.response.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 1302,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: String
    ): PokemonDetailResponse

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: String
    ): PokemonSpeciesResponse

}
