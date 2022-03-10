package com.zhengdianfang.atrs.repository.api

import com.zhengdianfang.atrs.repository.dto.OrderRefundRequestDTO
import com.zhengdianfang.atrs.repository.dto.OrderRefundResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApis {
    @POST("/flights-contract/orders/{oid}/refund")
    suspend fun refundOrder(@Path("oid") orderId: Long, @Body body: OrderRefundRequestDTO): OrderRefundResponseDTO
}