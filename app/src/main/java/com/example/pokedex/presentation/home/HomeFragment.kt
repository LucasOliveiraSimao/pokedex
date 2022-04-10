package com.example.pokedex.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var adapter: HomeAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initRecyclerview()
        initObserver()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (this::adapter.isInitialized) {
            adapter.clear()
        }
    }

    private fun initRecyclerview() {
        adapter = HomeAdapter()
        layoutManager = LinearLayoutManager(requireContext())

        binding.rvHome.layoutManager = layoutManager
        binding.rvHome.adapter = adapter
        binding.rvHome.addOnScrollListener(this@HomeFragment.scrollListener)
    }

    private fun initObserver() {
        viewModel.page = 0
        viewModel.executeFetchPokemon()
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                State.Loading -> {
                    binding.pbHome.visibility = View.VISIBLE
                }

                is State.Failure -> {
                    binding.pbHome.visibility = View.GONE
                    viewModel.isLoading.value = false
                }

                is State.Success -> {
                    binding.pbHome.visibility = View.GONE
                    viewModel.isLastPage = it.pokemonModel.next == null
                    viewModel.page++
                    adapter.updateList(it.pokemonModel.pokemonResults)
                    viewModel.isLoading.value = false
                }
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 0) {
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLastPage && !viewModel.isLoading.value!!) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= viewModel.pageSize
                    ) {
                        viewModel.executeFetchPokemon()
                    }
                }
            }
        }
    }
}