package com.moneyflow.tracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallets")
data class Wallet(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val icon: String = "💼",
    val limit: Double = 0.0,  // Limite de dépenses (optionnel)
    val balance: Double = 0.0,  // Solde actuel
    val colorHex: String = "#00BCD4",  // Couleur cyan par défaut
    val isArchived: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val walletType: WalletType = WalletType.EXPENSE  // Type de wallet
) {
    val remaining: Double
        get() = if (limit > 0) limit - balance else 0.0
    
    val percentUsed: Float
        get() = if (limit > 0) ((balance / limit) * 100).toFloat().coerceIn(0f, 100f) else 0f
    
    val isOverLimit: Boolean
        get() = limit > 0 && balance > limit
}

enum class WalletType {
    EXPENSE,    // Wallet de dépenses
    SAVINGS,    // Wallet d'épargne
    INCOME      // Wallet de revenus
}
