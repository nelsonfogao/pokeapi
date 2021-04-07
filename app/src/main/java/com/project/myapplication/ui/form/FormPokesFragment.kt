package com.project.myapplication.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.myapplication.database.AppUtil
import com.project.myapplication.PokemonRecyclerAdapter
import com.project.myapplication.R
import com.project.myapplication.database.PokemonFirestoreDao
import kotlinx.android.synthetic.main.form_pokes_fragment.*
import kotlinx.android.synthetic.main.list_fragment.*

class FormPokesFragment : Fragment() {

    private lateinit var formViewModel: FormPokesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.form_pokes_fragment, container, false)
        val application = requireActivity().application
        val formViewModelFactory =
            FormViewModelFactory(PokemonFirestoreDao(), application)


        formViewModel = ViewModelProvider(
            this, formViewModelFactory)
            .get(FormPokesViewModel::class.java)

        formViewModel.pokemon.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty())
                recycleViewTodosPokemon.adapter = PokemonRecyclerAdapter(it) {
                    AppUtil.pokemonSelecionado = it
                    var nome = it.name.toString()
                    formViewModel.salvarPokemons(nome)
                    findNavController().navigate(R.id.listFragment)
                }
                    recycleViewTodosPokemon.layoutManager = LinearLayoutManager(requireContext())
        })

        return view
    }

}