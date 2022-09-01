package com.shipment.destination.allocation.model

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomDbModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "shipment_allocation.db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideConversationDao(appDatabase: AppDatabase): ShipmentDriverDao {
        return appDatabase.shipmentDriverDao()
    }
}