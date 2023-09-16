package com.susumunoda.android.firebase.firestore

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreServiceImpl : FirestoreService {
    private val db = Firebase.firestore

    override suspend fun getDocument(path: String, id: String) =
        suspendCoroutine { cont ->
            db.collection(path)
                .document(id)
                .get()
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

    override suspend fun addDocument(path: String, value: Any) =
        suspendCoroutine { cont ->
            db.collection(path)
                .add(value)
                .addOnSuccessListener { cont.resume(Unit) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

    override suspend fun setDocument(path: String, id: String, value: Any) =
        suspendCoroutine { cont ->
            db.collection(path)
                .document(id)
                .set(value)
                .addOnSuccessListener { cont.resume(Unit) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

    override fun getDocumentsFlow(
        path: String,
        filters: List<Filter>,
        order: Order?
    ) = getQuerySnapshotsFlow(path, filters, order).map { it.documents }

    override fun getDocumentChangesFlow(
        path: String,
        filters: List<Filter>,
        order: Order?
    ) = getQuerySnapshotsFlow(path, filters, order).map { it.documentChanges }

    private fun getQuerySnapshotsFlow(
        path: String,
        filters: List<Filter> = emptyList(),
        order: Order? = null
    ): Flow<QuerySnapshot> {
        var query: Query = db.collection(path)
        for (filter in filters) {
            query = query.where(filter)
        }
        if (order != null) {
            query = query.orderBy(order.field, order.direction)
        }
        return query.snapshots()
    }
}
