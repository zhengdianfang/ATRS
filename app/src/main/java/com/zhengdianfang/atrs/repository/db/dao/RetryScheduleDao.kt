package com.zhengdianfang.atrs.repository.db.dao

import androidx.room.*
import com.zhengdianfang.atrs.repository.db.entity.RetrySchedule

@Dao
interface RetryScheduleDao {
    @Insert
    suspend fun insertSchedule(schedule: RetrySchedule): Long

    @Query("SELECT * FROM retry_schedule WHERE id=:id")
    suspend fun findScheduleById(id: Long): RetrySchedule?

    @Update
    suspend fun updateSchedule(schedule: RetrySchedule)

    @Delete
    fun deleteSchedule(retrySchedule: RetrySchedule)
}