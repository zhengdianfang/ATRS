package com.zhengdianfang.atrs.repository.db

import androidx.room.Room
import com.zhengdianfang.atrs.AppApplication
import com.zhengdianfang.atrs.repository.db.entity.RetrySchedule
import org.jetbrains.annotations.TestOnly

class RetryScheduleDBRepository {
    private var db =
        Room.databaseBuilder(AppApplication.instance, AppDatabase::class.java, "atrs-db")
            .build()

    suspend fun insertSchedule(retrySchedule: RetrySchedule): Long {
        return db.useOrderDao().insertSchedule(retrySchedule)
    }

    suspend fun updateSchedule(retrySchedule: RetrySchedule) {
        db.useOrderDao().updateSchedule(retrySchedule)
    }

    suspend fun findSchedule(id: Long): RetrySchedule? {
        return db.useOrderDao().findScheduleById(id)
    }

    suspend fun deleteSchedule(retrySchedule: RetrySchedule) {
        db.useOrderDao().deleteSchedule(retrySchedule)
    }

    @TestOnly
    fun setTestDB(db: AppDatabase) {
        this.db = db
    }
}