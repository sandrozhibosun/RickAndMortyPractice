package com.example.buildforpractice.feature.characters.domain.usecase

import com.example.buildforpractice.feature.characters.data.repository.CharactersRepository
import com.example.buildforpractice.feature.characters.data.repository.PagingCharactersRepository
import javax.inject.Inject

class GetCharacterPagingUsecase @Inject constructor(
    private val pagingCharactersRepository: PagingCharactersRepository
) {

    operator fun invoke() = pagingCharactersRepository.getCharactersByPage()
}