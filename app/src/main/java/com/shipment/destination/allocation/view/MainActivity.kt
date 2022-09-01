package com.shipment.destination.allocation.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shipment.destination.allocation.R
import com.shipment.destination.allocation.databinding.ActivityMainBinding
import com.shipment.destination.allocation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding){
            setContentView(this.root)
            progressIndicator.visibility = View.GONE
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ShipmentListFragment())
            .commitNow()
    }
}