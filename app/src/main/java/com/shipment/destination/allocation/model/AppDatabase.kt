package com.shipment.destination.allocation.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShipmentDriver::class, ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shipmentDriverDao(): ShipmentDriverDao
}