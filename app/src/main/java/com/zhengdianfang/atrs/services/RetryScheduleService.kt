package com.zhengdianfang.atrs.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.gson.Gson
import com.zhengdianfang.atrs.presenter.model.MakeInvoiceInformation
import com.zhengdianfang.atrs.repository.db.RetryScheduleDBRepository
import com.zhengdianfang.atrs.repository.db.entity.ScheduleType
import com.zhengdianfang.atrs.repository.remote.OrderRemoteRepository
import com.zhengdianfang.atrs.repository.remote.dto.ResponseCode
import kotlinx.coroutines.*

class RetryScheduleService : Service() {
    private val retryScheduleRepository = RetryScheduleDBRepository()
    private val orderRemoteRepository = OrderRemoteRepository()
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val scheduleId = intent?.getLongExtra("scheduleId", 0) ?: 0
        if (scheduleId > 0) {
            GlobalScope.launch(Dispatchers.IO) {
                val schedule = retryScheduleRepository.findSchedule(scheduleId)
                delay(10 * 1000)
                Log.d("ATRS", "start retry ${schedule?.retryCount}")
                if (schedule != null && schedule.taskType == ScheduleType.MAKE_INVOICE && schedule.retryCount > 0) {
                    schedule.retryCount -= 1
                    retryScheduleRepository.updateSchedule(schedule)
                    val response = orderRemoteRepository.makeVoice(schedule.requestId, Gson().fromJson(schedule.requestBody, MakeInvoiceInformation::class.java).transformToMakeInvoiceRequestDTO())
                    if (response.code == ResponseCode.SUCCESS) {
                        retryScheduleRepository.deleteSchedule(schedule)
                        stopSelf()
                    } else {
                        startService(Intent(this@RetryScheduleService, RetryScheduleService::class.java).putExtra("scheduleId", scheduleId))
                    }
                } else {
                    stopSelf()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}