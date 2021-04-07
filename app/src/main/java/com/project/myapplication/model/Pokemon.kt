package com.project.myapplication.model

import com.google.firebase.firestore.DocumentId

open class Pokemon (
    val name: String? = null,
    @DocumentId
    val id: String? = null,
){
    override fun toString(): String = "$name"
}