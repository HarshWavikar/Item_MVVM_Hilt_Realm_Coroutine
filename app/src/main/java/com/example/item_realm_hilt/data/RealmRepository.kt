package com.example.item_realm_hilt.data

import android.util.Log
import com.example.item_realm_hilt.model.Item
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class RealmRepository(val realm: Realm) : Repository {

    override suspend fun insertItem(item: Item) {
        realm.write { copyToRealm(item)}
    }

    override suspend fun updateItem(item: Item) {
        realm.write {
            val queriedItem = query<Item>(query = " id == $0", item.id).first().find()
            queriedItem?.item_name = item.item_name
            queriedItem?.item_price = item.item_price
        }
    }

    override suspend fun deleteItem(id: ObjectId) {
        realm.write {
            val item = query<Item>(query = "id == $0", id).first().find()
            try {
                item?.let { delete(it) }
            }catch (e: Exception){
                Log.d("Harsh", "${e.message}")
            }
        }
    }

    override fun getItems(): Flow<List<Item>> {
        return realm.query<Item>().asFlow().map { it.list }
    }
}