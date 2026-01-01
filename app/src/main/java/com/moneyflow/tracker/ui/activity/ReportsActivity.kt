package com.moneyflow.tracker.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moneyflow.tracker.databinding.ActivityReportsBinding
import com.moneyflow.tracker.ui.viewmodel.MoneyFlowViewModel
import java.text.NumberFormat
import java.util.*

class ReportsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportsBinding
    private val viewModel: MoneyFlowViewModel by viewModels()
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        loadReports()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Reports"
        
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadReports() {
        // Monthly flow
        viewModel.calculateMonthlyFlow { inflow, outflow, net ->
            runOnUiThread {
                binding.tvMonthlyInflow.text = currencyFormat.format(inflow)
                binding.tvMonthlyOutflow.text = currencyFormat.format(outflow)
                binding.tvNetFlow.text = currencyFormat.format(net)
                
                // Savings rate
                if (inflow > 0) {
                    val savingsRate = ((inflow - outflow) / inflow * 100).toInt()
                    binding.tvSavingsRate.text = "$savingsRate%"
                } else {
                    binding.tvSavingsRate.text = "0%"
                }
            }
        }

        // Note about future charts
        binding.tvChartPlaceholder.text = """
            📊 Charts Coming Soon!
            
            Future updates will include:
            • Monthly flow line chart
            • Expense breakdown pie chart
            • Category spending trends
            • Savings progress
        """.trimIndent()
    }
}
