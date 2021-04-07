package com.project.myapplication.ui.form

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.myapplication.database.PokemonDao

class FormViewModelFactory (
    val pokemonDao: PokemonDao,
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormPokesViewModel::class.java)){
            return FormPokesViewModel(pokemonDao, application) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }

}