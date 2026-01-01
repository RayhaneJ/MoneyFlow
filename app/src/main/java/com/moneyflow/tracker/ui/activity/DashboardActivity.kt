package com.moneyflow.tracker.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.moneyflow.tracker.R
import com.moneyflow.tracker.data.model.Wallet
import com.moneyflow.tracker.databinding.ActivityDashboardBinding
import com.moneyflow.tracker.ui.adapter.WalletAdapter
import com.moneyflow.tracker.ui.viewmodel.MoneyFlowViewModel
import java.text.NumberFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: MoneyFlowViewModel by viewModels()
    private lateinit var walletAdapter: WalletAdapter
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setupRecyclerView() {
        walletAdapter = WalletAdapter(
            onWalletClick = { wallet ->
                val intent = Intent(this, WalletDetailsActivity::class.java)
                intent.putExtra("WALLET_ID", wallet.id)
                startActivity(intent)
            },
            onWalletLongClick = { wallet ->
                showWalletOptions(wallet)
            }
        )

        binding.rvWallets.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = walletAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reports -> {
                startActivity(Intent(this, ReportsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupClickListeners() {
        binding.fabAddExpense.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        binding.btnCreateWallet.setOnClickListener {
            startActivity(Intent(this, CreateWalletActivity::class.java))
        }

        binding.btnAddInflow.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            intent.putExtra("FLOW_TYPE", "INFLOW")
            startActivity(intent)
        }

        binding.btnAddOutflow.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            intent.putExtra("FLOW_TYPE", "OUTFLOW")
            startActivity(intent)
        }
    }

    private fun observeData() {
        // Observe wallets
        viewModel.allWallets.observe(this) { wallets ->
            if (wallets.isEmpty()) {
                binding.tvNoWallets.visibility = View.VISIBLE
                binding.rvWallets.visibility = View.GONE
            } else {
                binding.tvNoWallets.visibility = View.GONE
                binding.rvWallets.visibility = View.VISIBLE
                walletAdapter.submitList(wallets)
            }
        }

        // Observe monthly flow
        updateMonthlyFlow()
    }

    private fun updateMonthlyFlow() {
        viewModel.calculateMonthlyFlow { inflow, outflow, net ->
            runOnUiThread {
                binding.tvMonthlyInflow.text = currencyFormat.format(inflow)
                binding.tvMonthlyOutflow.text = currencyFormat.format(outflow)
                binding.tvNetFlow.text = currencyFormat.format(net)
            }
        }
    }

    private fun showWalletOptions(wallet: Wallet) {
        val options = arrayOf(
            "View Details",
            "Edit Wallet",
            if (wallet.isArchived) "Unarchive" else "Archive",
            "Delete"
        )

        AlertDialog.Builder(this)
            .setTitle(wallet.name)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val intent = Intent(this, WalletDetailsActivity::class.java)
                        intent.putExtra("WALLET_ID", wallet.id)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(this, CreateWalletActivity::class.java)
                        intent.putExtra("WALLET_ID", wallet.id)
                        startActivity(intent)
                    }
                    2 -> {
                        viewModel.archiveWallet(wallet.id, !wallet.isArchived)
                    }
                    3 -> showDeleteConfirmation(wallet)
                }
            }
            .show()
    }

    private fun showDeleteConfirmation(wallet: Wallet) {
        AlertDialog.Builder(this)
            .setTitle("Delete Wallet")
            .setMessage("Are you sure you want to delete \"${wallet.name}\"? All associated expenses will also be deleted.")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteWallet(wallet)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        updateMonthlyFlow()
    }
}
