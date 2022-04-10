package com.example.pokedex.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.model.PokemonResult

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

    fun clear(){
        listPokemon.clear()
    }
}

class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.item_name)

    fun bind(contents: PokemonResult) {
        name.text = contents.name
    }
}