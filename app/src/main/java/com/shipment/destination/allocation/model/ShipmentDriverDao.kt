package com.shipment.destination.allocation.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShipmentDriverDao {

    @Query("SELECT * FROM shipmentDriver ORDER BY SS DESC")
    fun getSortedList(): List<ShipmentDriver>

    @Query("DELETE FROM shipmentDriver WHERE shipment = :shipment")
    fun deleteShipment(shipment: String)

    @Query("DELETE FROM shipmentDriver WHERE driver = :driver")
    fun deleteDriver(driver: String)

    @Query("SELECT COUNT(*) FROM shipmentDriver")
    fun getCount(): Int

    @Insert
    fun insertShipmentDriver(shipmentDriver: ShipmentDriver)

    @Query("DELETE FROM shipmentDriver")
    fun deleteAll()
}