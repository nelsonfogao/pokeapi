package com.project.myapplication.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.project.myapplication.model.Pokemon

interface PokemonDao {

    fun create(pokemon: Pokemon): Task<DocumentReference>
    fun update(pokemon: Pokemon): Task<Void>
    fun read(id: String): Task<DocumentSnapshot>
    fun delete(pokemon: Pokemon): Task<Void>
    fun all(): CollectionReference
    fun allDocuments(): Task<QuerySnapshot>
}