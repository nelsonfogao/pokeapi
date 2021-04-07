package com.project.myapplication.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.project.myapplication.api.ApiClient
import com.project.myapplication.database.PokemonDao
import com.project.myapplication.database.UsuarioFirebaseDao
import com.project.myapplication.model.Pokemon
import com.project.myapplication.model.Usuario
import kotlinx.coroutines.launch

class DetailsViewModel (
    private val pokemonDao: PokemonDao,
    application: Application
) : AndroidViewModel(application) {

    private val app = application


    private val _pokemons = MutableLiveData<Pokemon>()
    val pokemons: LiveData<Pokemon> = _pokemons

    private val _listPokemons = MutableLiveData<List<Pokemon>>()
    val listPokemons: LiveData<List<Pokemon>> = _listPokemons

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String?>()
    val msg: LiveData<String?> = _msg

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    init {
        _status.value = false
        _msg.value = null
        viewModelScope.launch {
            try {
                val response =
                        ApiClient
                                .getPokemonService().all(0, 1118)
                val data = response.results
                _listPokemons.value = data!!
            } catch (e: Exception){
                Log.i("PokemonResponse",
                        "${e.message}")
            }
        }
    }

    fun atualizarPokemons(nome:String, id: String) {
        _status.value = false
        _msg.value = "Por favor, aguarde a persistência!"

        val pokemons = Pokemon(nome, id)

        pokemonDao.update(pokemons)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Persistência realizada!"
            }
            .addOnFailureListener {
                _msg.value = "Persistência falhou!"
                Log.e("PokemonDaoFirebase", "${it.message}")
            }
    }

    fun deletarPokemon(pokemons: Pokemon) {
        _status.value = false
        _msg.value = "Por favor, aguarde a deleção!"

        pokemonDao.delete(pokemons)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Deleção realizada!"
            }
            .addOnFailureListener {
                _msg.value = "deleção falhou!"
            }
    }


    fun getUsuario(): String? {
        return UsuarioFirebaseDao.mAuth.currentUser.email
    }

    fun selectPokemon(id: String) {
        pokemonDao.read(id)
            .addOnSuccessListener {
                val pokemons = it.toObject(Pokemon::class.java)
                if (pokemons != null)
                    _pokemons.value = pokemons!!
                else
                    _msg.value = "O pokemon foi encontrada."
            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }

    }
}