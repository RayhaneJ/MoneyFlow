package com.moneyflow.tracker.data.repository

import androidx.lifecycle.LiveData
import com.moneyflow.tracker.data.dao.ExpenseDao
import com.moneyflow.tracker.data.dao.MonthlyOverviewDao
import com.moneyflow.tracker.data.dao.WalletDao
import com.moneyflow.tracker.data.model.*

class MoneyFlowRepository(
    private val walletDao: WalletDao,
    private val expenseDao: ExpenseDao,
    private val overviewDao: MonthlyOverviewDao
) {
    
    // Wallet operations
    val allWallets: LiveData<List<Wallet>> = walletDao.getAllActiveWallets()
    
    suspend fun insertWallet(wallet: Wallet): Long {
        return walletDao.insertWallet(wallet)
    }
    
    suspend fun updateWallet(wallet: Wallet) {
        walletDao.updateWallet(wallet)
    }
    
    suspend fun deleteWallet(wallet: Wallet) {
        walletDao.deleteWallet(wallet)
    }
    
    suspend fun getWalletById(walletId: Long): Wallet? {
        return walletDao.getWalletById(walletId)
    }
    
    fun getWalletByIdLive(walletId: Long): LiveData<Wallet?> {
        return walletDao.getWalletByIdLive(walletId)
    }
    
    fun getWalletsByType(type: WalletType): LiveData<List<Wallet>> {
        return walletDao.getWalletsByType(type)
    }
    
    suspend fun archiveWallet(walletId: Long, isArchived: Boolean) {
        walletDao.archiveWallet(walletId, isArchived)
    }
    
    suspend fun getTotalByWalletType(type: WalletType): Double {
        return walletDao.getTotalByType(type) ?: 0.0
    }
    
    // Expense operations
    val recentExpenses: LiveData<List<Expense>> = expenseDao.getRecentExpenses()
    
    suspend fun insertExpense(expense: Expense): Long {
        val id = expenseDao.insertExpense(expense)
        updateWalletBalance(expense.walletId)
        return id
    }
    
    suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense)
        updateWalletBalance(expense.walletId)
    }
    
    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
        updateWalletBalance(expense.walletId)
    }
    
    fun getExpensesByWallet(walletId: Long): LiveData<List<Expense>> {
        return expenseDao.getExpensesByWallet(walletId)
    }
    
    fun searchExpenses(query: String): LiveData<List<Expense>> {
        return expenseDao.searchExpenses(query)
    }
    
    suspend fun getTotalFlowInPeriod(flowType: FlowType, startTime: Long, endTime: Long): Double {
        return expenseDao.getTotalFlowInPeriod(flowType, startTime, endTime) ?: 0.0
    }
    
    private suspend fun updateWalletBalance(walletId: Long) {
        val inflow = expenseDao.getTotalByWalletAndType(walletId, FlowType.INFLOW) ?: 0.0
        val outflow = expenseDao.getTotalByWalletAndType(walletId, FlowType.OUTFLOW) ?: 0.0
        val balance = outflow  // Pour les wallets de dépenses, on compte les sorties
        walletDao.updateBalance(walletId, balance)
    }
    
    // Monthly overview operations
    val allOverviews: LiveData<List<MonthlyOverview>> = overviewDao.getAllMonthlyOverviews()
    
    suspend fun insertOverview(overview: MonthlyOverview): Long {
        return overviewDao.insertOverview(overview)
    }
    
    suspend fun updateOverview(overview: MonthlyOverview) {
        overviewDao.updateOverview(overview)
    }
    
    suspend fun getOverviewByMonth(yearMonth: String): MonthlyOverview? {
        return overviewDao.getOverviewByMonth(yearMonth)
    }
    
    fun getOverviewByMonthLive(yearMonth: String): LiveData<MonthlyOverview?> {
        return overviewDao.getOverviewByMonthLive(yearMonth)
    }
}
