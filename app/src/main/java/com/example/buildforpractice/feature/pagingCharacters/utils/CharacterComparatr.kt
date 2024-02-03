package com.example.buildforpractice.feature.pagingCharacters.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter

object CharacterComparator : DiffUtil.ItemCallback<RMCharacter>() {

    override fun areItemsTheSame(oldItem: RMCharacter, newItem: RMCharacter) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: RMCharacter, newItem: RMCharacter) =
        oldItem == newItem
}