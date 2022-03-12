package com.zhengdianfang.atrs.repository.remote

import com.zhengdianfang.atrs.repository.remote.api.ApiClient
import com.zhengdianfang.atrs.repository.remote.api.OrderApis
import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceRequestDTO
import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceResponseDTO
import com.zhengdianfang.atrs.repository.remote.dto.OrderRefundRequestDTO
import com.zhengdianfang.atrs.repository.remote.dto.OrderRefundResponseDTO
import org.jetbrains.annotations.TestOnly

open class OrderRemoteRepository {
    private var orderApis: OrderApis = ApiClient.INSTANCE.create(OrderApis::class.java)
    suspend fun refundOrderRequest(orderId: Long, reason: String): OrderRefundResponseDTO {
        return orderApis.refundOrder(orderId, OrderRefundRequestDTO(reason))
    }

    suspend fun makeVoice(orderId: Long, requestBody: MakeInvoiceRequestDTO): MakeInvoiceResponseDTO {
        return orderApis.makeVoice(orderId , requestBody)
    }

    @TestOnly
    fun setOrderApis(apis: OrderApis) {
        this.orderApis = apis
    }

}