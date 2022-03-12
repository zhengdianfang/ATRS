package com.zhengdianfang.atrs.repository.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.zhengdianfang.atrs.repository.db.entity.RetrySchedule
import com.zhengdianfang.atrs.repository.db.entity.ScheduleType
import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceRequestDTO
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RetryScheduleDBRepositoryTest : TestCase() {
    private lateinit var db: AppDatabase

    @Before
    public override fun setUp() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java).build()
    }

    @Test
    fun should_insert_success_when_bff_server_is_error () = runBlocking {
        val retryScheduleRepository = RetryScheduleDBRepository()
        retryScheduleRepository.setTestDB(db)
        val expect = RetrySchedule(
            taskType = ScheduleType.MAKE_INVOICE,
            requestId = 133,
            requestBody = Gson().toJson(MakeInvoiceRequestDTO("xxx公司", "wrong tax id", "xxxx@gmail.com", "13245432356")),
        )
        val expectId = retryScheduleRepository.insertSchedule(expect)
        val actual = retryScheduleRepository.findSchedule(expectId)
        assertEquals(actual?.requestBody, expect.requestBody)
    }


    @After
    fun closeDB() {
        db.clearAllTables()
        db.close()
    }
}