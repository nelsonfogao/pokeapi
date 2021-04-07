package com.project.myapplication.ui.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.myapplication.database.PokemonDao

class DetailsViewModelFactory(
    val pokemonDao: PokemonDao,
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(pokemonDao, application) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }

}