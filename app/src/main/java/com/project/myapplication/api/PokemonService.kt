package com.project.myapplication.api

import com.project.myapplication.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon/")
    suspend fun all(
        @Query("offset") offset: Int = 20,
        @Query("limit") limit: Int = 20,
    ): Response
}