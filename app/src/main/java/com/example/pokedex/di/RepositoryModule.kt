package com.example.pokedex.di

import com.example.pokedex.data.api.PokemonEndPoint
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.repository.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun providePokemonRepository(pokemonEndPoint: PokemonEndPoint): PokemonRepository {
        return PokemonRepositoryImpl(pokemonEndPoint)
    }
}