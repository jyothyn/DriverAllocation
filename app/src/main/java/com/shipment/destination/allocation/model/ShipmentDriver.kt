package com.shipment.destination.allocation.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shipmentDriver")
data class ShipmentDriver(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "shipment") val shipment: String,
    @ColumnInfo(name = "driver") val driver: String? = null,
    @ColumnInfo(name = "ss") val ss: Double? = 0.0
)