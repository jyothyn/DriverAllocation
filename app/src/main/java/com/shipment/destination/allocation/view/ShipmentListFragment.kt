package com.shipment.destination.allocation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.shipment.destination.allocation.databinding.FragmentShipmentListBinding
import com.shipment.destination.allocation.viewmodel.MainViewModel

class ShipmentListFragment : Fragment() {
    private val mvm: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentShipmentListBinding.inflate(inflater)
        lifecycleScope.launchWhenResumed {
            mvm.state.collect {
                if (it.isNotEmpty())
                    binding.shipments.adapter = ShipmentRecyclerViewAdapter(it)
            }
        }
        return binding.root
    }
}