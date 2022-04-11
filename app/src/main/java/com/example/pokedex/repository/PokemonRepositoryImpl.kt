package com.example.pokedex.repository

import com.example.pokedex.data.api.PokemonEndPoint
import com.example.pokedex.data.model.DetailsPokemon
import com.example.pokedex.data.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val pokemonEndPoint: PokemonEndPoint) :
    PokemonRepository {

    override suspend fun fetchPokemon(limit: Int, offset: Int): Flow<Pokemon> = flow {
        emit(pokemonEndPoint.fetchPokemon(limit, offset))
    }

    override suspend fun getInfoPokemon(name: String): Flow<DetailsPokemon> = flow {
        emit(pokemonEndPoint.getInfoPokemon(name))
    }
}