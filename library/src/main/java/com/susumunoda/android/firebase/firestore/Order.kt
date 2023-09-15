package com.susumunoda.android.firebase.firestore

import com.google.firebase.firestore.Query.Direction

class Order internal constructor(val field: String, val direction: Direction) {
    companion object {
        fun ascending(field: String) = Order(field, Direction.ASCENDING)
        fun descending(field: String) = Order(field, Direction.DESCENDING)
    }
}
