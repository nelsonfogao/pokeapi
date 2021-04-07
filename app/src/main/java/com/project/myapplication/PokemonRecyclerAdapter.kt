package com.project.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.model.Pokemon
import kotlinx.android.synthetic.main.recyclerpokemon.view.*

class PokemonRecyclerAdapter (
    private val pokemon: List<Pokemon>,
    val actionClick: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonRecyclerAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val textNome: TextView = itemView.textViewRVNome
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recyclerpokemon,
                parent,
                false
            )
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonEscolhido = pokemon[position]

        holder.textNome.text = pokemonEscolhido.name


        holder.itemView.setOnClickListener {
            actionClick(pokemonEscolhido)
        }

    }

    override fun getItemCount(): Int = pokemon.size
}