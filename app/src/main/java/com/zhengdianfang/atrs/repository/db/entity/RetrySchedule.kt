package com.zhengdianfang.atrs.repository.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "retry_schedule")
data class RetrySchedule (
    val taskType: ScheduleType,
    val requestId: Long,
    val requestBody: String,
    var retryCount : Int = 3,
    @PrimaryKey val id: Long? = null,
)

enum class ScheduleType(val type: Int) {
    MAKE_INVOICE(1)
}