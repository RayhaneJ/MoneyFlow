package com.moneyflow.tracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moneyflow.tracker.data.model.Wallet
import com.moneyflow.tracker.data.model.WalletType

@Dao
interface WalletDao {
    
    @Query("SELECT * FROM wallets WHERE isArchived = 0 ORDER BY createdAt DESC")
    fun getAllActiveWallets(): LiveData<List<Wallet>>
    
    @Query("SELECT * FROM wallets WHERE id = :walletId")
    suspend fun getWalletById(walletId: Long): Wallet?
    
    @Query("SELECT * FROM wallets WHERE id = :walletId")
    fun getWalletByIdLive(walletId: Long): LiveData<Wallet?>
    
    @Query("SELECT * FROM wallets WHERE walletType = :type AND isArchived = 0")
    fun getWalletsByType(type: WalletType): LiveData<List<Wallet>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: Wallet): Long
    
    @Update
    suspend fun updateWallet(wallet: Wallet)
    
    @Delete
    suspend fun deleteWallet(wallet: Wallet)
    
    @Query("UPDATE wallets SET balance = :balance WHERE id = :walletId")
    suspend fun updateBalance(walletId: Long, balance: Double)
    
    @Query("UPDATE wallets SET isArchived = :isArchived WHERE id = :walletId")
    suspend fun archiveWallet(walletId: Long, isArchived: Boolean)
    
    @Query("SELECT SUM(balance) FROM wallets WHERE walletType = :type AND isArchived = 0")
    suspend fun getTotalByType(type: WalletType): Double?
    
    @Query("SELECT COUNT(*) FROM wallets WHERE isArchived = 0")
    suspend fun getActiveWalletCount(): Int
}
