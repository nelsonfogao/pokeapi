package com.project.myapplication.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.myapplication.PokemonRecyclerAdapter
import com.project.myapplication.R
import com.project.myapplication.database.AppUtil
import com.project.myapplication.database.PokemonFirestoreDao
import com.project.myapplication.database.UsuarioFirebaseDao
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        verificarUsuario()


        val view =  inflater.inflate(R.layout.list_fragment, container, false)

        val listPokemonViewModelFactory = ListPokemonViewModelFactory(PokemonFirestoreDao())
        listViewModel = ViewModelProvider(this, listPokemonViewModelFactory)
            .get(ListViewModel::class.java)


        listViewModel.pokemons.observe(viewLifecycleOwner, Observer {
            recycleViewMeusPokemons.adapter = PokemonRecyclerAdapter(it) {
                AppUtil.pokemonSelecionado = it
                findNavController().navigate(R.id.detailsFragment)
            }
            recycleViewMeusPokemons.layoutManager = LinearLayoutManager(requireContext())
        })
        listViewModel.atualizarListaPokemons()



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        floatingActionButtonAdicionarPokemon.setOnClickListener {
            AppUtil.pokemonSelecionado = null
            findNavController().navigate(R.id.formPokesFragment)
        }
    }

    fun verificarUsuario(){
        if (UsuarioFirebaseDao.mAuth.currentUser == null)
            findNavController().popBackStack()
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        buttonLogout.setOnClickListener {
//            listViewModel.encerrarSessao()
//            findNavController().navigate(R.id.loginFragment)
//
//        }
//    }

}