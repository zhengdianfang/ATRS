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
import com.zhengdianfang.atrs.repository.remote.dto.ResponseCode
import com.zhengdianfang.atrs.services.RetryScheduleService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly

open class OrderPresenter : BasePresenter() {
    private var testRetryScheduleDBRepository: RetryScheduleDBRepository? = null
    private var orderRemoteRepository = OrderRemoteRepository()
    private val retryScheduleRepository by lazy { testRetryScheduleDBRepository ?: RetryScheduleDBRepository() }

    @DelicateCoroutinesApi
    fun refundOrder(
        orderId: Long,
        reason: String,
        success: (RefundOrderResultModel) -> Unit,
        fail: (RefundOrderResultModel) -> Unit
    ) {
        GlobalScope.launch(ioDispatcher) {
            val response = orderRemoteRepository.refundOrderRequest(orderId, reason)
            val refundOrderModel = RefundOrderResultModel(response.msg)
            if (response.code === ResponseCode.SUCCESS) {
                withContext(mainDispatcher) {
                    success(refundOrderModel)
                }
            } else if (response.code === ResponseCode.BFF_SERVER_ERROR || response.code === ResponseCode.ORDER_EXPIRED_CODE) {
                withContext(mainDispatcher) {
                    fail(refundOrderModel)
                }
            }
        }
    }

    fun makeInvoice(
        orderId: Long,
        makeInvoiceInformation: MakeInvoiceInformation,
        success: (MakeInvoiceResultModel) -> Unit,
        fail: (MakeInvoiceResultModel) -> Unit,
        retry: ((id: Long) -> Unit)? = null,
    ) {
        GlobalScope.launch(ioDispatcher) {
            val response = orderRemoteRepository.makeVoice(
                orderId,
                makeInvoiceInformation.transformToMakeInvoiceRequestDTO()
            )
            val makeInvoiceResultModel = MakeInvoiceResultModel(response.msg)
            when (response.code) {
                ResponseCode.SUCCESS -> {
                    withContext(mainDispatcher) {
                        success(makeInvoiceResultModel)
                    }
                }
                ResponseCode.TAX_ID_NOT_EXIST -> {
                    withContext(mainDispatcher) {
                        fail(makeInvoiceResultModel)
                    }
                }
                ResponseCode.BFF_SERVER_ERROR -> {
                    val id = retryScheduleRepository.insertSchedule(
                        RetrySchedule(
                            taskType = ScheduleType.MAKE_INVOICE,
                            requestId = orderId,
                            requestBody = Gson().toJson(makeInvoiceInformation)
                        )
                    )
                    withContext(mainDispatcher) {
                        if (id > 0) {
                            retry?.invoke(id)
                        }
                        success(MakeInvoiceResultModel("开发票成功"))
                    }
                }
                else -> {
                    AppApplication.instance.stopService(Intent(AppApplication.instance, RetryScheduleService::class.java))
                }
            }
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