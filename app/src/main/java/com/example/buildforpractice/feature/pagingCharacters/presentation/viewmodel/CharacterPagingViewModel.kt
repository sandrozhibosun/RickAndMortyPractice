package com.example.buildforpractice.feature.pagingCharacters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.feature.characters.domain.usecase.GetCharacterPagingUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharactersPagingViewModel @Inject constructor(private val getCharacterPagingUsecase: GetCharacterPagingUsecase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<RMCharacter>>(PagingData.empty())
    val uiState = _uiState.asStateFlow()

    init {
        getCharacterPagingUsecase()
            .onEach { _uiState.value = it }
            .cachedIn(viewModelScope)
            .launchIn(viewModelScope)
    }

    fun refreshForCharacters() {
        getCharacterPagingUsecase()
            .onEach { _uiState.value = it }
            .cachedIn(viewModelScope)
            .launchIn(viewModelScope)
    }
}