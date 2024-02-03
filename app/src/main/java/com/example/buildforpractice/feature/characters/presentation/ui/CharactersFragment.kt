package com.example.buildforpractice.feature.characters.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.buildforpractice.R
import com.example.buildforpractice.databinding.FragmentCharactersBinding
import com.example.buildforpractice.feature.characters.presentation.viewmodel.CharacterViewModel
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
class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by viewModels()

    @Inject
    lateinit var charactersAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupRecyclerView()
    }

    private fun setupObserver() {
        viewModel.uiState.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show loading
                }

                is Resource.Success -> {
                    // Show data
                    binding.swipeContainer.isRefreshing = false
                    charactersAdapter.setCharactersList(resource.value)
                }

                is Resource.Failure -> {
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_SHORT).show()
                }

            }
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
                adapter = charactersAdapter
            }
            charactersAdapter.setOnItemClick {
                findNavController().navigate(
                    R.id.action_charactersFragment_to_episodesFragment,
                    Bundle().apply {
                        putStringArray("EpisodesArg", it.episodes.map { it.removePrefix("https://rickandmortyapi.com/api/episode/") }.toTypedArray())
                    }
                )
            }
            swipeContainer.setOnRefreshListener { viewModel.refreshForCharacters() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}