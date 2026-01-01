package com.moneyflow.tracker.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.moneyflow.tracker.databinding.ActivityWalletDetailsBinding
import com.moneyflow.tracker.ui.adapter.ExpenseAdapter
import com.moneyflow.tracker.ui.viewmodel.MoneyFlowViewModel
import java.text.NumberFormat
import java.util.*

class WalletDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalletDetailsBinding
    private val viewModel: MoneyFlowViewModel by viewModels()
    private lateinit var expenseAdapter: ExpenseAdapter
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    private var walletId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        walletId = intent.getLongExtra("WALLET_ID", -1)
        if (walletId == -1L) {
            finish()
            return
        }

        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        loadData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        expenseAdapter = ExpenseAdapter { expense ->
            // TODO: Show expense details or edit
        }

        binding.rvExpenses.apply {
            layoutManager = LinearLayoutManager(this@WalletDetailsActivity)
            adapter = expenseAdapter
        }
    }

    private fun setupClickListeners() {
        binding.fabAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            intent.putExtra("WALLET_ID", walletId)
            startActivity(intent)
        }
    }

    private fun loadData() {
        // Load wallet details
        viewModel.getWalletById(walletId).observe(this) { wallet ->
            wallet?.let {
                supportActionBar?.title = it.name
                binding.tvWalletIcon.text = it.icon
                binding.tvWalletName.text = it.name
                
                if (it.limit > 0) {
                    binding.tvBudgeted.text = "Limit: ${currencyFormat.format(it.limit)}"
                    binding.tvRemaining.text = "Remaining: ${currencyFormat.format(it.remaining)}"
                } else {
                    binding.tvBudgeted.text = "No limit set"
                    binding.tvRemaining.visibility = View.GONE
                }
                
                binding.tvSpent.text = "Spent: ${currencyFormat.format(it.balance)}"
            }
        }

        // Load expenses
        viewModel.getExpensesByWallet(walletId).observe(this) { expenses ->
            if (expenses.isEmpty()) {
                binding.tvNoExpenses.visibility = View.VISIBLE
                binding.rvExpenses.visibility = View.GONE
            } else {
                binding.tvNoExpenses.visibility = View.GONE
                binding.rvExpenses.visibility = View.VISIBLE
                expenseAdapter.submitList(expenses)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}
