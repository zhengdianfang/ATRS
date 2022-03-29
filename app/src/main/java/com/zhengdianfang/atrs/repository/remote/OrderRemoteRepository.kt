package com.zhengdianfang.atrs.repository.remote

import com.google.gson.Gson
import com.zhengdianfang.atrs.repository.remote.api.ApiClient
import com.zhengdianfang.atrs.repository.remote.api.OrderApis
import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceRequestDTO
import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceResponseDTO
import com.zhengdianfang.atrs.repository.remote.dto.OrderRefundRequestDTO
import com.zhengdianfang.atrs.repository.remote.dto.OrderRefundResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.jetbrains.annotations.TestOnly
import retrofit2.HttpException

open class OrderRemoteRepository {
    private var orderApis: OrderApis = ApiClient.INSTANCE.create(OrderApis::class.java)
    suspend fun refundOrderRequest(orderId: Long, reason: String): OrderRefundResponseDTO {
        return orderApis.refundOrder(orderId, OrderRefundRequestDTO(reason))
    }

    suspend fun makeVoice(orderId: Long, requestBody: MakeInvoiceRequestDTO): Flow<MakeInvoiceResponseDTO> {
        return orderApis.makeVoice(orderId , requestBody).catch { e ->
            if (e is HttpException) {
                emit(Gson().fromJson(e.response()?.errorBody()?.string(), MakeInvoiceResponseDTO::class.java))
            }
        }
    }

    @TestOnly
    fun setOrderApis(apis: OrderApis) {
        this.orderApis = apis
    }

}