package com.example.buildforpractice.feature.characters.domain.usecase

import com.example.buildforpractice.feature.characters.data.repository.CharactersRepository
import javax.inject.Inject

class GetCharacterUsecase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {

    operator fun invoke() = charactersRepository.getCharactersNetworkFirst()
}