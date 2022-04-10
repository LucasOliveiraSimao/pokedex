package com.example.pokedex.di

import com.example.pokedex.data.api.PokemonEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EndPointModule {
    @Singleton
    @Provides
    fun providePokemonEndPoint(retrofit: Retrofit): PokemonEndPoint {
        return retrofit.create(PokemonEndPoint::class.java)
    }
}