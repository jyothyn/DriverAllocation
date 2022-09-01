package com.shipment.destination.allocation.view

import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shipment.destination.allocation.databinding.FragmentShipmentBinding
import com.shipment.destination.allocation.model.ShipmentDriver

class ShipmentRecyclerViewAdapter(
    private val driverAllocationList: List<ShipmentDriver>
) : RecyclerView.Adapter<ShipmentRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentShipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = driverAllocationList[position]
        holder.bindVal(item)
    }

    override fun getItemCount(): Int = driverAllocationList.size

    inner class ViewHolder(binding: FragmentShipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val driver: TextView = binding.driverName
        private val address: TextView = binding.shipmentAddress
        fun bindVal(item: ShipmentDriver) {
            driver.text = item.driver
            address.text = item.shipment
            itemView.setOnClickListener {
                address.visibility = address.visibility.let {
                    if (it == INVISIBLE) VISIBLE else INVISIBLE
                }
            }
        }
    }
}