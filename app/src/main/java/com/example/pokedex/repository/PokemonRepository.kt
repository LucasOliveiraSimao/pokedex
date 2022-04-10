package com.example.pokedex.repository

import com.example.pokedex.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemon(limit: Int, offset: Int): Flow<Pokemon>
}