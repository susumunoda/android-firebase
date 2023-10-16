package com.susumunoda.android.firebase.firestore

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.flow.Flow

interface FirestoreService {
    suspend fun getDocument(path: String, id: String): DocumentSnapshot
    suspend fun addDocument(path: String, value: Any): DocumentReference
    suspend fun setDocument(path: String, id: String, value: Any)
    fun getDocumentsFlow(
        path: String,
        filters: List<Filter> = emptyList(),
        order: Order? = null
    ): Flow<List<DocumentSnapshot>>

    fun getDocumentChangesFlow(
        path: String,
        filters: List<Filter> = emptyList(),
        order: Order? = null
    ): Flow<List<DocumentChange>>
}