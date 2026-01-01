package com.moneyflow.tracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moneyflow.tracker.data.model.MonthlyOverview

@Dao
interface MonthlyOverviewDao {
    
    @Query("SELECT * FROM monthly_overview ORDER BY yearMonth DESC")
    fun getAllMonthlyOverviews(): LiveData<List<MonthlyOverview>>
    
    @Query("SELECT * FROM monthly_overview WHERE yearMonth = :yearMonth")
    suspend fun getOverviewByMonth(yearMonth: String): MonthlyOverview?
    
    @Query("SELECT * FROM monthly_overview WHERE yearMonth = :yearMonth")
    fun getOverviewByMonthLive(yearMonth: String): LiveData<MonthlyOverview?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOverview(overview: MonthlyOverview): Long
    
    @Update
    suspend fun updateOverview(overview: MonthlyOverview)
    
    @Delete
    suspend fun deleteOverview(overview: MonthlyOverview)
    
    @Query("SELECT * FROM monthly_overview ORDER BY yearMonth DESC LIMIT :limit")
    fun getRecentOverviews(limit: Int): LiveData<List<MonthlyOverview>>
}
