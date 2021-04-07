package com.project.myapplication.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.myapplication.database.PokemonDao
import com.project.myapplication.database.UsuarioFirebaseDao
import com.project.myapplication.model.Pokemon
import com.project.myapplication.model.Usuario

class ListViewModel (
    private val pokemonDao: PokemonDao
) : ViewModel() {

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> = _pokemons

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    fun atualizarListaPokemons() {

        pokemonDao.all().addSnapshotListener { snapshot, error ->
            if (error != null)
                Log.i("ListViewModel", "${error.message}")
            else
                if (snapshot != null && !snapshot.isEmpty) {
                    val pokemons =
                        snapshot.toObjects(Pokemon::class.java)
                    _pokemons.value = pokemons
                }
        }

    }

    fun encerrarSessao() {
        UsuarioFirebaseDao.encerrarSessao()
        _usuario.value = null
    }
}