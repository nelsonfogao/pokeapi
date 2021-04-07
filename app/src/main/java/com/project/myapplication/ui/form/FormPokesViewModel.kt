package com.project.myapplication.ui.form

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.project.myapplication.api.ApiClient
import com.project.myapplication.database.PokemonDao
import com.project.myapplication.model.Pokemon
import com.project.myapplication.model.Usuario
import kotlinx.coroutines.launch

class FormPokesViewModel(
    private val pokemonDao: PokemonDao,
    application: Application
) : AndroidViewModel(application) {

    private val app = application

    private val _pokemon = MutableLiveData<List<Pokemon>>()
    val pokemon: LiveData<List<Pokemon>> = _pokemon


    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String?>()
    val msg: LiveData<String?> = _msg

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    init {
        viewModelScope.launch {
            try {
                val response =
                    ApiClient
                        .getPokemonService().all(0, 1118)
                val data = response.results
                _pokemon.value = data!!
            } catch (e: Exception){
                Log.i("PokemonResponse",
                    "${e.message}")
            }
        }
    }
    fun salvarPokemons(nome:String) {
        _status.value = false
        _msg.value = "Por favor, aguarde a persistência!"

        val pokemons = Pokemon(nome)

        pokemonDao.create(pokemons)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Persistência realizada!"
            }
            .addOnFailureListener {
                _msg.value = "Persistência falhou!"
                Log.e("PokemonDaoFirebase", "${it.message}")
            }
    }


}