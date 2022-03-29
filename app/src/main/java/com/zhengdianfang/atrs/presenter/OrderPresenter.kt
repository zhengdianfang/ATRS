package com.zhengdianfang.atrs.presenter

import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.zhengdianfang.atrs.AppApplication
import com.zhengdianfang.atrs.presenter.model.MakeInvoiceInformation
import com.zhengdianfang.atrs.presenter.model.MakeInvoiceResultModel
import com.zhengdianfang.atrs.presenter.model.RefundOrderResultModel
import com.zhengdianfang.atrs.repository.db.RetryScheduleDBRepository
import com.zhengdianfang.atrs.repository.db.entity.RetrySchedule
import com.zhengdianfang.atrs.repository.db.entity.ScheduleType
import com.zhengdianfang.atrs.repository.remote.OrderRemoteRepository
import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceResponseDTO
import com.zhengdianfang.atrs.repository.remote.dto.OrderRefundResponseDTO
import com.zhengdianfang.atrs.repository.remote.dto.ResponseCode
import com.zhengdianfang.atrs.services.RetryScheduleService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly
import retrofit2.HttpException

open class OrderPresenter : BasePresenter() {
    private var testRetryScheduleDBRepository: RetryScheduleDBRepository? = null
    private var orderRemoteRepository = OrderRemoteRepository()
    private val retryScheduleRepository by lazy {
        testRetryScheduleDBRepository ?: RetryScheduleDBRepository()
    }

    @DelicateCoroutinesApi
    fun refundOrder(orderId: Long, reason: String) {
//        GlobalScope.launch(ioDispatcher) {
//            val response =
//            val refundOrderModel = RefundOrderResultModel(response.msg)
//            if (response.code === ResponseCode.SUCCESS) {
//                withContext(mainDispatcher) {
//                    success(refundOrderModel)
//                }
//            } else if (response.code === ResponseCode.BFF_SERVER_ERROR || response.code === ResponseCode.ORDER_EXPIRED_CODE) {
//                withContext(mainDispatcher) {
//                    fail(refundOrderModel)
//                }
//            }
//        }
    }

    suspend fun makeInvoice(
        orderId: Long,
        makeInvoiceInformation: MakeInvoiceInformation,
    ): Flow<MakeInvoiceResultModel> {
        return orderRemoteRepository.makeVoice(
            orderId,
            makeInvoiceInformation.transformToMakeInvoiceRequestDTO()
        ).map {
            val makeInvoiceResultModel = MakeInvoiceResultModel(it.msg)
            makeInvoiceResultModel
        }
    }

    @TestOnly
    fun setTestOrderRemoteRepository(testRepository: OrderRemoteRepository) {
        this.orderRemoteRepository = testRepository
    }

    @TestOnly
    fun setTestRetryScheduleRepository(testDBRepository: RetryScheduleDBRepository) {
        this.testRetryScheduleDBRepository = testDBRepository
    }
}