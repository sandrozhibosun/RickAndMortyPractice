package com.example.buildforpractice.feature.characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.feature.characters.domain.usecase.GetCharacterUsecase
import com.example.buildforpractice.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val getCharacterUsecase: GetCharacterUsecase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<RMCharacter>>>(Resource.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCharacterUsecase()
            .onStart { emit(Resource.Loading) }
            .catch { emit(Resource.Failure(false, null, null)) }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }

    fun refreshForCharacters() {
        getCharacterUsecase()
            .onStart { emit(Resource.Loading) }
            .catch { emit(Resource.Failure(false, null, null)) }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }
}