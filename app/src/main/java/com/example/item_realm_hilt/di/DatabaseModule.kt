package com.example.item_realm_hilt.di

import com.example.item_realm_hilt.data.Repository
import com.example.item_realm_hilt.data.RealmRepository
import com.example.item_realm_hilt.model.Item
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesRealm(): Realm {
        val config = RealmConfiguration.Builder(schema = setOf(Item::class))
            .name("items.realm")
            .schemaVersion(1)
            .build()

        return Realm.open(config)
    }

    @Singleton
    @Provides
    fun providesMongoRepository(realm: Realm): Repository {
        return RealmRepository(realm)
    }

}