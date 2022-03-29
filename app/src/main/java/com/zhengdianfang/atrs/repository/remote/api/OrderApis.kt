package com.zhengdianfang.atrs.repository.remote.api

import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceRequestDTO
import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceResponseDTO
import com.zhengdianfang.atrs.repository.remote.dto.OrderRefundRequestDTO
import com.zhengdianfang.atrs.repository.remote.dto.OrderRefundResponseDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApis {
    @POST("/flights-contract/orders/{oid}/refund")
    suspend fun refundOrder(@Path("oid") orderId: Long, @Body body: OrderRefundRequestDTO): OrderRefundResponseDTO

    @POST("flights-contract/orders/{oid}/invoice")
    suspend fun makeVoice(@Path("oid") orderId: Long, @Body body: MakeInvoiceRequestDTO) : Flow<MakeInvoiceResponseDTO>
}