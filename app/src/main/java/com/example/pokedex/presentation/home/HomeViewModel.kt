package com.example.pokedex.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.repository.PokemonRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepositoryImpl: PokemonRepositoryImpl
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    var page = 0
    var pageSize = 20
    var isLastPage = false
    var isLoading = MutableLiveData<Boolean>().apply { value = false }

    fun executeFetchPokemon() {
        isLoading.value = true
        viewModelScope.launch {
            pokemonRepositoryImpl.fetchPokemon(pageSize, page * pageSize)
                .flowOn(Dispatchers.IO)
                .onStart { _state.value = State.Loading }
                .catch { _state.value = State.Failure(it.message) }
                .collect { _state.value = State.Success(it) }
        }
    }
}

sealed class State {
    object Loading : State()
    data class Success(val pokemonModel: Pokemon) : State()
    data class Failure(val failure: String?) : State()
}