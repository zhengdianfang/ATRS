package com.zhengdianfang.atrs.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhengdianfang.atrs.repository.db.dao.RetryScheduleDao
import com.zhengdianfang.atrs.repository.db.entity.RetrySchedule

@Database(entities = [RetrySchedule::class], version = 1)
open abstract class AppDatabase : RoomDatabase() {
    abstract fun useOrderDao(): RetryScheduleDao
}