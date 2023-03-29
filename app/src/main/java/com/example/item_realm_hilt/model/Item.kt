package com.example.item_realm_hilt.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Item : RealmObject {

    @PrimaryKey
    var id : ObjectId = ObjectId.invoke()
    var item_name :String = ""
    var item_price:String = ""
}