package com.project.myapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var instance: Retrofit? = null
    private fun getRetrofit(): Retrofit {
        if (instance == null)
            instance = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return instance as Retrofit
    }

    fun getPokemonService(): PokemonService
            = getRetrofit().create(PokemonService::class.java)
}