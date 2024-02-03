package com.example.buildforpractice.feature.episodes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.feature.episodes.data.model.domain.Episode
import com.example.buildforpractice.feature.episodes.domain.GetEpisodesByIdsUsecase
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
class EpisodesViewModel @Inject constructor(
    private val getEpisodesByIdsUsecase: GetEpisodesByIdsUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<Episode>>>(Resource.Loading)
    val uiState = _uiState.asStateFlow()

    fun getEpisodesByIds(episodesIds: List<String>) {
        getEpisodesByIdsUsecase(episodesIds)
            .onStart { emit(Resource.Loading) }
            .catch { emit(Resource.Failure(false, null, "viewmodel")) }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }
}