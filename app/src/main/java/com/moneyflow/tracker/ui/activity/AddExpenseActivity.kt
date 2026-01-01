package com.moneyflow.tracker.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moneyflow.tracker.R
import com.moneyflow.tracker.data.model.Expense
import com.moneyflow.tracker.data.model.FlowType
import com.moneyflow.tracker.data.model.Wallet
import com.moneyflow.tracker.databinding.ActivityAddExpenseBinding
import com.moneyflow.tracker.ui.viewmodel.MoneyFlowViewModel

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding
    private val viewModel: MoneyFlowViewModel by viewModels()
    private var wallets = listOf<Wallet>()
    private var selectedWalletId: Long = -1
    private var selectedFlowType = FlowType.OUTFLOW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get flow type from intent if provided
        val flowTypeExtra = intent.getStringExtra("FLOW_TYPE")
        if (flowTypeExtra != null) {
            selectedFlowType = FlowType.valueOf(flowTypeExtra)
        }

        setupToolbar()
        setupFlowTypeButtons()
        setupClickListeners()
        loadWallets()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Expense"
        
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupFlowTypeButtons() {
        when (selectedFlowType) {
            FlowType.INFLOW -> binding.btnInflow.isChecked = true
            FlowType.OUTFLOW -> binding.btnOutflow.isChecked = true
        }

        binding.flowTypeGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                selectedFlowType = when (checkedId) {
                    R.id.btnInflow -> FlowType.INFLOW
                    else -> FlowType.OUTFLOW
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnSave.setOnClickListener {
            saveExpense()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun loadWallets() {
        viewModel.allWallets.observe(this) { walletList ->
            wallets = walletList.filter { !it.isArchived }
            
            if (wallets.isEmpty()) {
                Toast.makeText(this, "Please create a wallet first", Toast.LENGTH_LONG).show()
                finish()
                return@observe
            }

            val walletNames = wallets.map { it.name }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, walletNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerWallet.adapter = adapter

            binding.spinnerWallet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedWalletId = wallets[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun saveExpense() {
        val amountText = binding.etAmount.text.toString().trim()
        val title = binding.etTitle.text.toString().trim()
        val memo = binding.etMemo.text.toString().trim()
        val tags = binding.etTags.text.toString().trim()
        val location = binding.etLocation.text.toString().trim()

        // Validation
        if (amountText.isEmpty()) {
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show()
            return
        }

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedWalletId == -1L) {
            Toast.makeText(this, "Please select a wallet", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val expense = Expense(
            walletId = selectedWalletId,
            amount = amount,
            title = title,
            flowType = selectedFlowType,
            memo = memo,
            tags = tags,
            location = location,
            timestamp = System.currentTimeMillis()
        )

        viewModel.insertExpense(expense)
        Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()
        finish()
    }
}
