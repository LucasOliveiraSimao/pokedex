package com.example.pokedex.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.model.PokemonResult
import com.example.pokedex.utils.Const

class HomeAdapter : RecyclerView.Adapter<HomeViewHolder>() {

    private var listPokemon = ArrayList<PokemonResult>()

    fun updateList(items: List<PokemonResult>) {
        listPokemon.addAll(items)
        notifyItemChanged(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(listPokemon[position])
    }

    override fun getItemCount() = listPokemon.size

    fun clear() {
        listPokemon.clear()
    }
}

class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.item_name)

    fun bind(contents: PokemonResult) {
        name.text = contents.name

        val id = getIdPokemon(contents)

        val info = arrayOf(
            id, contents.name
        )

        itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putStringArray(Const.key, info)
            }
            itemView.findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment, bundle
            )
        }
    }

    private fun getIdPokemon(contents: PokemonResult): String {
        val id = if (contents.url.endsWith("/")) {
            contents.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            contents.url.takeLastWhile { it.isDigit() }
        }
        return id
    }
}