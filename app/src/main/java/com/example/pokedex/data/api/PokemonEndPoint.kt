package com.example.pokedex.data.api

import com.example.pokedex.data.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonEndPoint {
    @GET("pokemon/")
    suspend fun fetchPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Pokemon
}