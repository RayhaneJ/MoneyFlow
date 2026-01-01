package com.moneyflow.tracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moneyflow.tracker.data.model.Expense
import com.moneyflow.tracker.data.model.FlowType

@Dao
interface ExpenseDao {
    
    @Query("SELECT * FROM expenses ORDER BY timestamp DESC LIMIT 50")
    fun getRecentExpenses(): LiveData<List<Expense>>
    
    @Query("SELECT * FROM expenses WHERE walletId = :walletId ORDER BY timestamp DESC")
    fun getExpensesByWallet(walletId: Long): LiveData<List<Expense>>
    
    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    suspend fun getExpenseById(expenseId: Long): Expense?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense): Long
    
    @Update
    suspend fun updateExpense(expense: Expense)
    
    @Delete
    suspend fun deleteExpense(expense: Expense)
    
    @Query("SELECT SUM(amount) FROM expenses WHERE walletId = :walletId AND flowType = :flowType")
    suspend fun getTotalByWalletAndType(walletId: Long, flowType: FlowType): Double?
    
    @Query("SELECT * FROM expenses WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    fun getExpensesByDateRange(startTime: Long, endTime: Long): LiveData<List<Expense>>
    
    @Query("SELECT * FROM expenses WHERE title LIKE '%' || :query || '%' OR memo LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchExpenses(query: String): LiveData<List<Expense>>
    
    @Query("DELETE FROM expenses WHERE walletId = :walletId")
    suspend fun deleteExpensesByWallet(walletId: Long)
    
    @Query("SELECT SUM(amount) FROM expenses WHERE flowType = :flowType AND timestamp BETWEEN :startTime AND :endTime")
    suspend fun getTotalFlowInPeriod(flowType: FlowType, startTime: Long, endTime: Long): Double?
}
