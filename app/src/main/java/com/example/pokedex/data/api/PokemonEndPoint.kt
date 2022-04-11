package com.example.pokedex.data.api

import com.example.pokedex.data.model.DetailsPokemon
import com.example.pokedex.data.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonEndPoint {
    @GET("pokemon/")
    suspend fun fetchPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Pokemon

    @GET("pokemon/{name}")
    suspend fun getInfoPokemon(
        @Path("name") name: String,
    ): DetailsPokemon
}