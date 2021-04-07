package com.project.myapplication.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.project.myapplication.model.Pokemon

class PokemonFirestoreDao :PokemonDao {

    private val collection = FirebaseFirestore
        .getInstance()
        .collection("meuspokemons")



    override fun create(pokemon: Pokemon): Task<DocumentReference> {
        return collection
            .add(pokemon)
    }
    override fun update(pokemon: Pokemon): Task<Void> {
        return collection
                .document(pokemon.id!!)
                .set(pokemon)
    }

    override fun read(id: String): Task<DocumentSnapshot> {
        return collection
            .document(id)
            .get()
    }
    override fun delete(pokemon: Pokemon): Task<Void> {
        return collection
            .document(pokemon.id!!)
            .delete()
    }

    override fun all(): CollectionReference {
        return collection
    }

    override fun allDocuments(): Task<QuerySnapshot> {
        return collection.get()
    }

}