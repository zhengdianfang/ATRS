package com.zhengdianfang.atrs.presenter

import com.zhengdianfang.atrs.presenter.model.RefundOrderModel
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
    fun refundOrder(orderId: Long, reason: String, success: (RefundOrderModel) -> Unit, fail: (RefundOrderModel) -> Unit) {
        GlobalScope.launch(ioDispatcher) {
            val response = orderRemoteRepository.refundOrderRequest(orderId, reason)
            val refundOrderModel = RefundOrderModel(response.msg)
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

    @TestOnly
    fun setTestOrderRemoteRepository(testRepository: OrderRemoteRepository) {
       this.orderRemoteRepository = testRepository
    }
}