package com.moneyflow.tracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_overview")
data class MonthlyOverview(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val yearMonth: String, // Format: "2025-12"
    val totalInflow: Double = 0.0,
    val totalOutflow: Double = 0.0,
    val netFlow: Double = 0.0,  // Inflow - Outflow
    val savingsRate: Float = 0f,  // Pourcentage épargné
    val updatedAt: Long = System.currentTimeMillis()
) {
    val flowBalance: Double
        get() = totalInflow - totalOutflow
}
