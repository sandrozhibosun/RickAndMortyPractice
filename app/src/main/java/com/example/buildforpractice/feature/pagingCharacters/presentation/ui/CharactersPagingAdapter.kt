package com.example.buildforpractice.feature.characters.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.buildforpractice.R
import com.example.buildforpractice.databinding.ItemCharacterBinding
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.feature.pagingCharacters.utils.CharacterComparator
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CharactersPagingAdapter @Inject constructor(
    @ActivityContext private val context: Context
) :
    PagingDataAdapter<RMCharacter, CharactersPagingAdapter.CharactersItemViewHolder>(CharacterComparator) {

    private var mCharactersList = emptyList<RMCharacter>()
    private lateinit var itemCharacterBinding: ItemCharacterBinding
    private var onItemClick: ((RMCharacter) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setOnItemClick(onItemClick: ((RMCharacter) -> Unit)? = null) {
        this.onItemClick = onItemClick
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersItemViewHolder {
        itemCharacterBinding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersItemViewHolder(itemCharacterBinding, context)
    }

    override fun onBindViewHolder(holder: CharactersItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onItemClick)
        }
    }

    override fun getItemCount(): Int {
        return mCharactersList.size
    }

    inner class CharactersItemViewHolder(
        private val binding: ItemCharacterBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rMCharacter: RMCharacter, onItemClick: ((RMCharacter) -> Unit)? = null) {
            with(binding) {
                Glide.with(context).load(rMCharacter.image).apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                ).into(displayCharacter)
                characterName.text = rMCharacter.name
                characterSpecies.text = rMCharacter.species
                characterStatus.text = rMCharacter.status
                binding.root.setOnClickListener { onItemClick?.invoke(rMCharacter)}
            }
        }
    }
}