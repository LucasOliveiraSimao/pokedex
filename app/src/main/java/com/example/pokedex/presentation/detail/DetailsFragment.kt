package com.example.pokedex.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.data.model.Ability
import com.example.pokedex.databinding.FragmentDetailsBinding
import com.example.pokedex.utils.Const
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()
    private var url = ""
    private var name = ""
    private var idPokemon = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        getData()
        initObserver()

        return binding.root
    }

    private fun initObserver() {
        viewModel.executeGetInfoPokemon(name)
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                State.Loading -> {
                    binding.pbDetails.visibility = View.VISIBLE
                }

                is State.Failure -> {
                    binding.pbDetails.visibility = View.GONE
                }

                is State.Success -> {
                    binding.pbDetails.visibility = View.GONE
                    setInfo(it.pokemonModel.abilities)
                }
            }
        }
    }

    private fun getData() {
        val info = arguments?.getStringArray(Const.key)!!

        idPokemon = info[0].toInt()
        name = info[1]
        url = Const.urlSpritesPokemon + "$idPokemon.png"

    }

    private fun setInfo(abilities: List<Ability>) {
        binding.tvAbilitiesOne.text = abilities[0].ability.name
        binding.tvAbilitiesTwo.text = abilities[1].ability.name

        binding.tvName.text = name
        binding.tvAbilities.text = resources.getText(R.string.label_abilities)

        Glide.with(requireContext()).load(url).into(binding.ivPokemon)
    }

}