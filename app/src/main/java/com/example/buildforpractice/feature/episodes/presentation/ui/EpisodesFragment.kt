package com.example.buildforpractice.feature.episodes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.buildforpractice.R
import com.example.buildforpractice.databinding.FragmentCharactersBinding
import com.example.buildforpractice.feature.characters.presentation.ui.CharactersAdapter
import com.example.buildforpractice.feature.characters.presentation.viewmodel.CharacterViewModel
import com.example.buildforpractice.feature.episodes.presentation.viewmodel.EpisodesViewModel
import com.example.buildforpractice.utils.network.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: EpisodesViewModel by viewModels()
    private lateinit var episodesIds: List<String>

    @Inject
    lateinit var episodesAdapter: EpisodesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharactersBinding.inflate(inflater, container, false)

        val args: EpisodesFragmentArgs by navArgs()
        episodesIds = args.EpisodesArg.toList()

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
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    // Show data
                    binding.swipeContainer.isRefreshing = false
                    binding.progressBar.visibility = View.INVISIBLE
                    episodesAdapter.setEpisodesList(resource.value)
                }

                is Resource.Failure -> {
                    binding.swipeContainer.isRefreshing = false
                    binding.progressBar.visibility = View.INVISIBLE
                    Snackbar.make(
                        binding.root,
                        resource.errorDescription ?: "",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            }
        }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    private fun setupRecyclerView() {
        viewModel.getEpisodesByIds(episodesIds)
        with(binding) {
            // Set the adapter
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = episodesAdapter
            }
            episodesAdapter.setOnItemClick {
                findNavController().navigate(R.id.action_episodesFragment_to_charactersPagingFragment)
            }
            swipeContainer.setOnRefreshListener { viewModel.getEpisodesByIds(episodesIds) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}