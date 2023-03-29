package com.example.item_realm_hilt.data

import com.example.item_realm_hilt.model.Item
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface Repository {

    suspend fun insertItem (item: Item)

    suspend fun updateItem (item: Item)

    suspend fun deleteItem (id: ObjectId)

    fun getItems() : Flow<List<Item>>

}