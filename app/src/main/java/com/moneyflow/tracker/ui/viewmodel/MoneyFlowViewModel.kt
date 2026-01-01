package com.moneyflow.tracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.moneyflow.tracker.data.database.MoneyFlowDatabase
import com.moneyflow.tracker.data.model.*
import com.moneyflow.tracker.data.repository.MoneyFlowRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MoneyFlowViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: MoneyFlowRepository
    val allWallets: LiveData<List<Wallet>>
    val recentExpenses: LiveData<List<Expense>>
    
    init {
        val database = MoneyFlowDatabase.getDatabase(application)
        repository = MoneyFlowRepository(
            database.walletDao(),
            database.expenseDao(),
            database.monthlyOverviewDao()
        )
        allWallets = repository.allWallets
        recentExpenses = repository.recentExpenses
    }
    
    // Wallet operations
    fun insertWallet(wallet: Wallet) = viewModelScope.launch {
        repository.insertWallet(wallet)
    }
    
    fun updateWallet(wallet: Wallet) = viewModelScope.launch {
        repository.updateWallet(wallet)
    }
    
    fun deleteWallet(wallet: Wallet) = viewModelScope.launch {
        repository.deleteWallet(wallet)
    }
    
    fun getWalletById(walletId: Long): LiveData<Wallet?> {
        return repository.getWalletByIdLive(walletId)
    }
    
    fun getWalletsByType(type: WalletType): LiveData<List<Wallet>> {
        return repository.getWalletsByType(type)
    }
    
    fun archiveWallet(walletId: Long, isArchived: Boolean) = viewModelScope.launch {
        repository.archiveWallet(walletId, isArchived)
    }
    
    // Expense operations
    fun insertExpense(expense: Expense) = viewModelScope.launch {
        repository.insertExpense(expense)
    }
    
    fun updateExpense(expense: Expense) = viewModelScope.launch {
        repository.updateExpense(expense)
    }
    
    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        repository.deleteExpense(expense)
    }
    
    fun getExpensesByWallet(walletId: Long): LiveData<List<Expense>> {
        return repository.getExpensesByWallet(walletId)
    }
    
    fun searchExpenses(query: String): LiveData<List<Expense>> {
        return repository.searchExpenses(query)
    }
    
    // Overview operations
    fun getCurrentMonthOverview(): LiveData<MonthlyOverview?> {
        val currentMonth = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())
        return repository.getOverviewByMonthLive(currentMonth)
    }
    
    // Statistics
    fun getTotalByWalletType(type: WalletType, callback: (Double) -> Unit) = viewModelScope.launch {
        val total = repository.getTotalByWalletType(type)
        callback(total)
    }
    
    fun calculateMonthlyFlow(callback: (inflow: Double, outflow: Double, net: Double) -> Unit) = viewModelScope.launch {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startOfMonth = calendar.timeInMillis
        
        val now = System.currentTimeMillis()
        
        val inflow = repository.getTotalFlowInPeriod(FlowType.INFLOW, startOfMonth, now)
        val outflow = repository.getTotalFlowInPeriod(FlowType.OUTFLOW, startOfMonth, now)
        val net = inflow - outflow
        
        callback(inflow, outflow, net)
    }
}
