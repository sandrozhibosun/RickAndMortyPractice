package com.example.buildforpractice.feature.characters.presentation.ui

import PagingHeaderLoadStateAdapter
import PagingLoadStateAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.paging.cachedIn
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildforpractice.R
import com.example.buildforpractice.databinding.FragmentCharacterPagingBinding
import com.example.buildforpractice.databinding.FragmentCharactersBinding
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.feature.characters.presentation.viewmodel.CharacterViewModel
import com.example.buildforpractice.feature.pagingCharacters.presentation.viewmodel.CharactersPagingViewModel
import com.example.buildforpractice.utils.network.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class CharactersPagingFragment : Fragment() {

    private var _binding: FragmentCharacterPagingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: CharactersPagingViewModel by viewModels()

    @Inject
    lateinit var charactersPagingAdapter: CharactersPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharacterPagingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupRecyclerView()
    }

    private fun setupObserver() {
        viewModel.uiState.onEach { pagingData ->

            charactersPagingAdapter.submitData(pagingData)

        }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    private fun setupRecyclerView() {
        with(binding) {
            // Set the adapter
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = charactersPagingAdapter.withLoadStateAdapters(
                    header = PagingHeaderLoadStateAdapter(charactersPagingAdapter),
                    footer = PagingLoadStateAdapter(charactersPagingAdapter)
                )
            }
            charactersPagingAdapter.setOnItemClick {
                findNavController().navigate(
                    R.id.action_charactersPagingFragment_to_episodesFragment,
                    Bundle().apply {
                        putStringArray("EpisodesArg", it.episodes.map { it.removePrefix("https://rickandmortyapi.com/api/episode/") }.toTypedArray())
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
        header: PagingHeaderLoadStateAdapter<RMCharacter, CharactersPagingAdapter.CharactersItemViewHolder> = PagingHeaderLoadStateAdapter(
            charactersPagingAdapter
        ),
        footer: PagingLoadStateAdapter<RMCharacter, CharactersPagingAdapter.CharactersItemViewHolder> = PagingLoadStateAdapter(
            charactersPagingAdapter
        )
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.refresh
            footer.loadState = loadStates.append
        }

        return ConcatAdapter(header, this, footer)
    }
}