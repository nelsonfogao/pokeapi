package com.project.myapplication.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.myapplication.database.PokemonDao

class ListPokemonViewModelFactory (
    private val pokemonDao: PokemonDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(pokemonDao) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }
}