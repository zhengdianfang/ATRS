package com.zhengdianfang.atrs.presenter

import com.zhengdianfang.atrs.presenter.model.MakeInvoiceInformation
import com.zhengdianfang.atrs.presenter.model.MakeInvoiceResultModel
import com.zhengdianfang.atrs.presenter.model.RefundOrderResultModel
import com.zhengdianfang.atrs.repository.OrderRemoteRepository
import com.zhengdianfang.atrs.repository.dto.ResponseCode
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly

open class OrderPresenter : BasePresenter() {
    private var orderRemoteRepository = OrderRemoteRepository()

    @DelicateCoroutinesApi
    fun refundOrder(orderId: Long, reason: String, success: (RefundOrderResultModel) -> Unit, fail: (RefundOrderResultModel) -> Unit) {
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

    fun makeInvoice(orderId: Long, makeInvoiceInformation: MakeInvoiceInformation, success: (MakeInvoiceResultModel) -> Unit, fail: (MakeInvoiceResultModel) -> Unit) {
        GlobalScope.launch(ioDispatcher) {
            val response = orderRemoteRepository.makeVoice(orderId, makeInvoiceInformation.transformToMakeInvoiceRequestDTO())
            val makeInvoiceResultModel = MakeInvoiceResultModel(response.msg)
            if (response.code === ResponseCode.SUCCESS) {
                withContext(mainDispatcher) {
                    success(makeInvoiceResultModel)
                }
            } else if (response.code === ResponseCode.TAX_ID_NOT_EXIST) {
                withContext(mainDispatcher) {
                    fail(makeInvoiceResultModel)
                }
            }
        }

    }

    @TestOnly
    fun setTestOrderRemoteRepository(testRepository: OrderRemoteRepository) {
        this.orderRemoteRepository = testRepository
    }
}