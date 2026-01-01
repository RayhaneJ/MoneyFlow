package com.moneyflow.tracker.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = Wallet::class,
            parentColumns = ["id"],
            childColumns = ["walletId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("walletId")]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val walletId: Long,
    val amount: Double,
    val title: String,
    val timestamp: Long = System.currentTimeMillis(),
    val flowType: FlowType = FlowType.OUTFLOW,
    val memo: String = "",
    val tags: String = "",  // Tags séparés par des virgules
    val location: String = ""  // Optionnel
)

enum class FlowType {
    INFLOW,   // Argent qui entre
    OUTFLOW   // Argent qui sort
}
