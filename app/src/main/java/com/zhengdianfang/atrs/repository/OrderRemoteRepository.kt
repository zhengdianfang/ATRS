package com.zhengdianfang.atrs.repository

import com.zhengdianfang.atrs.repository.api.ApiClient
import com.zhengdianfang.atrs.repository.api.OrderApis
import com.zhengdianfang.atrs.repository.dto.OrderRefundRequestDTO
import com.zhengdianfang.atrs.repository.dto.OrderRefundResponseDTO
import org.jetbrains.annotations.TestOnly

open class OrderRemoteRepository {
    private var orderApis: OrderApis = ApiClient.INSTANCE.create(OrderApis::class.java)
    suspend fun refundOrderRequest(orderId: Long, reason: String): OrderRefundResponseDTO {
        return orderApis.refundOrder(orderId, OrderRefundRequestDTO(reason))
    }

    @TestOnly
    fun setOrderApis(apis: OrderApis) {
        this.orderApis = apis
    }
}