package com.project.myapplication.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.myapplication.PokemonRecyclerAdapter
import com.project.myapplication.R
import com.project.myapplication.database.AppUtil
import com.project.myapplication.database.PokemonFirestoreDao
import com.project.myapplication.model.Pokemon
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.form_pokes_fragment.*

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.details_fragment, container, false)

        val application = requireActivity().application
        val detailsViewModelFactory =
            DetailsViewModelFactory(PokemonFirestoreDao(), application)

        detailsViewModel = ViewModelProvider(
            this, detailsViewModelFactory)
            .get(DetailsViewModel::class.java).apply {
                setUpMsgObserver(this)
                setUpStatusObserver(this)
            }

        detailsViewModel.pokemons.observe(viewLifecycleOwner, Observer {
            if (it != null){
                preencherFormulario(it)
            }
        })

        return view
    }

    private fun setUpStatusObserver(detailsViewModel: DetailsViewModel) {
        detailsViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if (status)
                findNavController().popBackStack()
        })
    }
    private fun setUpMsgObserver(detailsViewModel: DetailsViewModel) {
        detailsViewModel.msg.observe(viewLifecycleOwner, Observer { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (AppUtil.pokemonSelecionado != null) {
            detailsViewModel.selectPokemon(AppUtil.pokemonSelecionado!!.id!!)

        }

        detailsViewModel.listPokemons.observe(viewLifecycleOwner, Observer {
            var id = AppUtil.pokemonSelecionado?.id
            if (!it.isNullOrEmpty())

                recyclerViewAlterar.adapter = PokemonRecyclerAdapter(it) {
                    AppUtil.pokemonSelecionado = it
                    var nome = it.name.toString()
                    detailsViewModel.atualizarPokemons(nome,id!!)
                    findNavController().navigate(R.id.listFragment)
                }
            recyclerViewAlterar.layoutManager = LinearLayoutManager(requireContext())
        })
        floatingActionButtonDelete.setOnClickListener{
            detailsViewModel.deletarPokemon(AppUtil.pokemonSelecionado!!)
        }


    }
    private fun preencherFormulario(pokemons: Pokemon){

        textViewDetailsNome.setText(pokemons.name.toString())
    }
}