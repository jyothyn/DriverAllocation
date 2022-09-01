package com.shipment.destination.allocation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.shipment.destination.allocation.model.ShipmentDriver
import com.shipment.destination.allocation.model.ShipmentDriverDao
import com.shipment.destination.allocation.model.InputJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Integer.max
import java.lang.Integer.min
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val shipmentDriverDao: ShipmentDriverDao
) : ViewModel() {

    private val _allocationListState = MutableStateFlow<List<ShipmentDriver>>(emptyList())
    val state: StateFlow<List<ShipmentDriver>>
        get() = _allocationListState
    private val allocationList = mutableListOf<ShipmentDriver>()

    val jsonString = """{"shipments":["298 Heather Maple Approach","205 Sunny Vale","13 W Street",
        |"434 Hazy Driveway","37 Burning Highway","766 Rustic Circle","493 Spur Shore","943 Light Farms",
        |"741 Silent Isle","645 Iron Horse"],"drivers":["Ricarda Everaars","Deandre Tapia",
        |"Timothy Tukker","Cassandra Dillon","Lawrence Symons","Earlene Hammond","Leif Flynn",
        |"Francesco Bijl","Gabriel Mosley","Aron Parrish"]}""".trimMargin()

    init {
        //create the mapping of driver and address
        assignDrivers()
    }

    private fun assignDrivers() {
        val gson = Gson()
        val data = gson.fromJson(jsonString, InputJson::class.java)
        println("size = ${data.drivers?.size},${data.shipments?.size} ")
        viewModelScope.launch(Dispatchers.IO) {
            //reset database
            shipmentDriverDao.deleteAll()
//            println("is all deleted? ${shipmentDriverDao.getCount()}")
            data.drivers?.let {
                for (d in data.drivers) {
                    //create map of driver-name and vowel & consonant count
                    countDriverNameLetters(d)
                }
            }
            //for each address, insert all driver names, anc score
            data.shipments?.let { allShipments ->
                data.drivers?.let { allDrivers ->
                    for (addr: String in allShipments) {
                        val streetLength = getStreetNameSize(addr)
                        for (driver in allDrivers) insertToDB(addr, streetLength, driver)
                    }
                }
            }
            println(" count after inserted? ${shipmentDriverDao.getCount()}")
            sortDriverShipmentByMaxSS()
            // update flow with final list
            _allocationListState.value = allocationList
        }
    }

    private fun insertToDB(street: String, streetLen: Int, driver: String) {
        //if length of street name is even ss=vowel*1.5, else #consonant
        var ss = 0.0
        driverNameCount[driver]?.let { pair ->
            ss = if (streetLen % 2 == 0) pair.first * 1.5 else pair.second.toDouble()
        }
        //if street len & driver name length has common factor, then add 50% to ss
        if (hasCommonFactor(streetLen, driver.length)) ss *= 1.5
        shipmentDriverDao.insertShipmentDriver(
            ShipmentDriver(
                0,
                shipment = street,
                driver = driver,
                ss = ss
            )
        )
    }

    private fun sortDriverShipmentByMaxSS() {
        // sort list by the highest ss score
        var sortedList = shipmentDriverDao.getSortedList()
        while (sortedList.isNotEmpty()) {
            val shipmentDriver = sortedList[0]
            allocationList.add(shipmentDriver)
            // delete all records of that shipment and driver
            shipmentDriverDao.deleteShipment(shipmentDriver.shipment)
            shipmentDriver.driver?.let { shipmentDriverDao.deleteDriver(it) }
            sortedList = shipmentDriverDao.getSortedList()
        }
    }

    private fun getStreetNameSize(str: String): Int {
        val regex = "[a-zA-Z]".toRegex()
        val streetIndex = regex.find(str)
        val start = streetIndex?.range?.start ?: 0
//        println(" street name length $str -> ${str.length - start}")
        return str.length - start
    }

    // create a Map<DriverName, Pair<vowel, consonant>> so you don't have to calculate same again
    private var driverNameCount = mutableMapOf<String, Pair<Int, Int>>()
    private fun countDriverNameLetters(driverName: String) {
        var vowel = 0
        var conso = 0
        for (n: Char in driverName.lowercase()) {
            when (n) {
                'a', 'e', 'i', 'o', 'u' -> ++vowel
                in 'a'..'z' -> ++conso
            }
        }
        driverNameCount[driverName] = Pair(vowel, conso)
    }

    // create a Map<Pair<Int, Int>, Boolean> so you don't have to check same numbers again
    var commonFactorMap = mutableMapOf<Pair<Int, Int>, Boolean>()
    private fun hasCommonFactor(n1: Int, n2: Int): Boolean {
        commonFactorMap[Pair(n1, n2)]?.let { return it }
        if (n1 <= 1 || n2 <= 1) return false
        val mx = max(n1, n2)
        val mn = min(n1, n2)
        if (mx % mn == 0) return true
        var bool = false
        for (i in 2..mn / 2) {
            if (mn % i == 0 && mx % i == 0) {
                bool = true
                break
            }
        }
        // add it to map for reuse
        commonFactorMap[Pair(n1, n2)] = bool
        commonFactorMap[Pair(n2, n1)] = bool
        return bool
    }
}