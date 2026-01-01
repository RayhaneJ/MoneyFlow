package com.moneyflow.tracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.moneyflow.tracker.data.dao.ExpenseDao
import com.moneyflow.tracker.data.dao.MonthlyOverviewDao
import com.moneyflow.tracker.data.dao.WalletDao
import com.moneyflow.tracker.data.model.Expense
import com.moneyflow.tracker.data.model.MonthlyOverview
import com.moneyflow.tracker.data.model.Wallet

@Database(
    entities = [Wallet::class, Expense::class, MonthlyOverview::class],
    version = 1,
    exportSchema = false
)
abstract class MoneyFlowDatabase : RoomDatabase() {
    
    abstract fun walletDao(): WalletDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun monthlyOverviewDao(): MonthlyOverviewDao
    
    companion object {
        @Volatile
        private var INSTANCE: MoneyFlowDatabase? = null
        
        fun getDatabase(context: Context): MoneyFlowDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoneyFlowDatabase::class.java,
                    "moneyflow_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
