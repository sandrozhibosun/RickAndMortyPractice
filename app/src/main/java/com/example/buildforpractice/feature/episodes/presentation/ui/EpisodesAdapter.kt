package com.example.buildforpractice.feature.episodes.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.buildforpractice.R
import com.example.buildforpractice.databinding.ItemCharacterBinding
import com.example.buildforpractice.feature.episodes.data.model.domain.Episode
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class EpisodesAdapter @Inject constructor(
    @ActivityContext private val context: Context
) :
    RecyclerView.Adapter<EpisodesAdapter.EpisodesItemViewHolder>() {

    private var mEpisodesList = emptyList<Episode>()
    private lateinit var itemCharacterBinding: ItemCharacterBinding
    private var onItemClick: ((Episode) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setEpisodesList(
        mEpisodesList: List<Episode>,
    ) {
        this.mEpisodesList = mEpisodesList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOnItemClick(onItemClick: ((Episode) -> Unit)? = null) {
        this.onItemClick = onItemClick
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesItemViewHolder {
        itemCharacterBinding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodesItemViewHolder(itemCharacterBinding, context)
    }

    override fun onBindViewHolder(holder: EpisodesItemViewHolder, position: Int) {
        holder.bind(mEpisodesList[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return mEpisodesList.size
    }

    inner class EpisodesItemViewHolder(
        private val binding: ItemCharacterBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode, onItemClick: ((Episode) -> Unit)? = null) {
            with(binding) {
                Glide.with(context).load("").apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                ).into(displayCharacter)
                characterName.text = episode.name
                characterSpecies.text = episode.airDate
                characterStatus.text = episode.episodeCode
                binding.root.setOnClickListener { onItemClick?.invoke(episode) }
            }
        }
    }
}