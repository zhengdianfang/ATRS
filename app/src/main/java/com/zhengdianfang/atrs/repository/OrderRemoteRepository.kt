package com.zhengdianfang.atrs.repository

import com.zhengdianfang.atrs.repository.api.OrderApis
import com.zhengdianfang.atrs.repository.dto.OrderRefundRequestDTO
import com.zhengdianfang.atrs.repository.dto.OrderRefundResponseDTO

class OrderRemoteRepository(val orderApis: OrderApis) {
    suspend fun refundOrderRequest(orderId: Long, reason: String): OrderRefundResponseDTO {
        return orderApis.refundOrder(orderId, OrderRefundRequestDTO(reason))
    }
}