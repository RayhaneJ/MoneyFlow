package com.moneyflow.tracker.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moneyflow.tracker.R
import com.moneyflow.tracker.data.model.Wallet
import com.moneyflow.tracker.data.model.WalletType
import com.moneyflow.tracker.databinding.ActivityCreateWalletBinding
import com.moneyflow.tracker.ui.viewmodel.MoneyFlowViewModel

class CreateWalletActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateWalletBinding
    private val viewModel: MoneyFlowViewModel by viewModels()
    private var walletId: Long = -1
    private var existingWallet: Wallet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        walletId = intent.getLongExtra("WALLET_ID", -1)

        setupToolbar()
        setupSpinner()
        setupClickListeners()

        if (walletId != -1L) {
            loadWallet()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (walletId == -1L) {
            "Create Wallet"
        } else {
            "Edit Wallet"
        }
        
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupSpinner() {
        val walletTypes = arrayOf("Expense Wallet", "Savings Wallet", "Income Wallet")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, walletTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerWalletType.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.btnSave.setOnClickListener {
            saveWallet()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun loadWallet() {
        viewModel.getWalletById(walletId).observe(this) { wallet ->
            wallet?.let {
                existingWallet = it
                binding.etWalletName.setText(it.name)
                binding.etIcon.setText(it.icon)
                binding.etLimit.setText(if (it.limit > 0) it.limit.toString() else "")
                binding.etColor.setText(it.colorHex)
                
                // Set wallet type spinner
                val typeIndex = when (it.walletType) {
                    WalletType.EXPENSE -> 0
                    WalletType.SAVINGS -> 1
                    WalletType.INCOME -> 2
                }
                binding.spinnerWalletType.setSelection(typeIndex)
            }
        }
    }

    private fun saveWallet() {
        val name = binding.etWalletName.text.toString().trim()
        val icon = binding.etIcon.text.toString().trim()
        val limitText = binding.etLimit.text.toString().trim()
        val colorHex = binding.etColor.text.toString().trim()

        // Validation
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter wallet name", Toast.LENGTH_SHORT).show()
            return
        }

        val limit = limitText.toDoubleOrNull() ?: 0.0
        val walletType = when (binding.spinnerWalletType.selectedItemPosition) {
            0 -> WalletType.EXPENSE
            1 -> WalletType.SAVINGS
            2 -> WalletType.INCOME
            else -> WalletType.EXPENSE
        }

        val finalIcon = icon.ifEmpty { "💼" }
        val finalColor = if (colorHex.isNotEmpty() && colorHex.startsWith("#")) {
            colorHex
        } else {
            "#00BCD4"
        }

        if (existingWallet != null) {
            // Update existing wallet
            val updatedWallet = existingWallet!!.copy(
                name = name,
                icon = finalIcon,
                limit = limit,
                colorHex = finalColor,
                walletType = walletType
            )
            viewModel.updateWallet(updatedWallet)
            Toast.makeText(this, "Wallet updated", Toast.LENGTH_SHORT).show()
        } else {
            // Create new wallet
            val newWallet = Wallet(
                name = name,
                icon = finalIcon,
                limit = limit,
                colorHex = finalColor,
                walletType = walletType
            )
            viewModel.insertWallet(newWallet)
            Toast.makeText(this, "Wallet created", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
