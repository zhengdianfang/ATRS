package com.zhengdianfang.atrs.presenter

import com.zhengdianfang.atrs.presenter.model.RefundOrderModel
import com.zhengdianfang.atrs.repository.OrderRemoteRepository
import com.zhengdianfang.atrs.repository.dto.ResponseCode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly

open class OrderPresenter : BasePresenter() {
    private var orderRemoteRepository = OrderRemoteRepository()

    fun refundOrder(orderId: Long, reason: String, success: (RefundOrderModel) -> Unit) {
        GlobalScope.launch(ioDispatcher) {
            val response = orderRemoteRepository.refundOrderRequest(orderId, reason)
            if (response.code === ResponseCode.SUCCESS.code) {
                val refundOrderModel = RefundOrderModel(response.msg)
                withContext(mainDispatcher) {
                  success(refundOrderModel)
                }
            }
        }
    }

    @TestOnly
    fun setTestOrderRemoteRepository(testRepository: OrderRemoteRepository) {
       this.orderRemoteRepository = testRepository
    }
}