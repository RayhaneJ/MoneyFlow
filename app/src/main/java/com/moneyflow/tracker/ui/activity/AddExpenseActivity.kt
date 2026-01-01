package com.moneyflow.tracker.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.moneyflow.tracker.R
import com.moneyflow.tracker.data.model.Expense
import com.moneyflow.tracker.data.model.FlowType
import com.moneyflow.tracker.databinding.ActivityAddExpenseBinding
import com.moneyflow.tracker.ui.viewmodel.MoneyFlowViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAddExpenseBinding
    private val viewModel: MoneyFlowViewModel by viewModels()
    
    private var selectedWalletId: Long = -1
    private var selectedFlowType: FlowType = FlowType.OUTFLOW
    private var selectedDate: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Get wallet ID from intent if provided
        selectedWalletId = intent.getLongExtra("wallet_id", -1)
        
        setupUI()
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupUI() {
        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_expense)
        
        // Set default flow type buttons text
        binding.buttonInflow.text = getString(R.string.inflow)
        binding.buttonOutflow.text = getString(R.string.outflow)
        
        // Set initial date
        binding.textSelectedDate.text = dateFormat.format(selectedDate.time)
        
        // Set default flow type selection
        updateFlowTypeSelection(FlowType.OUTFLOW)
    }
    
    private fun setupObservers() {
        // Observe wallets for spinner/dropdown
        viewModel.allWallets.observe(this) { wallets ->
            // Setup wallet selection (you can implement a spinner or dropdown here)
            if (selectedWalletId != -1L) {
                val selectedWallet = wallets.find { it.id == selectedWalletId }
                selectedWallet?.let {
                    binding.textSelectedWallet.text = "${it.icon} ${it.name}"
                }
            } else if (wallets.isNotEmpty()) {
                // Select first wallet by default
                selectedWalletId = wallets[0].id
                binding.textSelectedWallet.text = "${wallets[0].icon} ${wallets[0].name}"
            }
        }
    }
    
    private fun setupClickListeners() {
        // Flow type buttons
        binding.buttonInflow.setOnClickListener {
            updateFlowTypeSelection(FlowType.INFLOW)
        }
        
        binding.buttonOutflow.setOnClickListener {
            updateFlowTypeSelection(FlowType.OUTFLOW)
        }
        
        // Date picker
        binding.layoutDatePicker.setOnClickListener {
            showDatePicker()
        }
        
        // Save button
        binding.buttonSave.setOnClickListener {
            saveExpense()
        }
        
        // Cancel button
        binding.buttonCancel.setOnClickListener {
            finish()
        }
        
        // Toolbar back button
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun updateFlowTypeSelection(flowType: FlowType) {
        selectedFlowType = flowType
        
        when (flowType) {
            FlowType.INFLOW -> {
                // Update button styles for inflow selection
                binding.buttonInflow.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.primary_color)
                )
                binding.buttonInflow.setTextColor(
                    ContextCompat.getColor(this, android.R.color.white)
                )
                binding.buttonOutflow.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.surface_variant)
                )
                binding.buttonOutflow.setTextColor(
                    ContextCompat.getColor(this, R.color.on_surface_variant)
                )
                
                // Update labels
                binding.textAmountLabel.text = getString(R.string.income_amount)
                binding.textDescriptionLabel.text = getString(R.string.income_description)
            }
            
            FlowType.OUTFLOW -> {
                // Update button styles for outflow selection
                binding.buttonOutflow.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.primary_color)
                )
                binding.buttonOutflow.setTextColor(
                    ContextCompat.getColor(this, android.R.color.white)
                )
                binding.buttonInflow.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.surface_variant)
                )
                binding.buttonInflow.setTextColor(
                    ContextCompat.getColor(this, R.color.on_surface_variant)
                )
                
                // Update labels
                binding.textAmountLabel.text = getString(R.string.expense_amount)
                binding.textDescriptionLabel.text = getString(R.string.expense_description)
            }
        }
    }
    
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                binding.textSelectedDate.text = dateFormat.format(selectedDate.time)
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    
    private fun saveExpense() {
        // Validate inputs
        val amountText = binding.editTextAmount.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val category = binding.editTextCategory.text.toString().trim()
        
        if (amountText.isEmpty()) {
            binding.editTextAmount.error = getString(R.string.error_amount_required)
            return
        }
        
        if (description.isEmpty()) {
            binding.editTextDescription.error = getString(R.string.error_description_required)
            return
        }
        
        if (selectedWalletId == -1L) {
            Toast.makeText(this, getString(R.string.error_select_wallet), Toast.LENGTH_SHORT).show()
            return
        }
        
        val amount = try {
            amountText.toDouble()
        } catch (e: NumberFormatException) {
            binding.editTextAmount.error = getString(R.string.error_invalid_amount)
            return
        }
        
        if (amount <= 0) {
            binding.editTextAmount.error = getString(R.string.error_amount_positive)
            return
        }
        
        // Create expense object
        val expense = Expense(
            walletId = selectedWalletId,
            amount = amount,
            description = description,
            category = category.ifEmpty { getString(R.string.default_category) },
            flowType = selectedFlowType,
            date = selectedDate.timeInMillis,
            createdAt = System.currentTimeMillis()
        )
        
        // Save expense using ViewModel
        lifecycleScope.launch {
            try {
                viewModel.insertExpense(expense)
                Toast.makeText(
                    this@AddExpenseActivity,
                    getString(R.string.expense_saved_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(
                    this@AddExpenseActivity,
                    getString(R.string.error_saving_expense),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}