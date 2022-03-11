package com.zhengdianfang.atrs.presenter

import com.zhengdianfang.atrs.presenter.model.RefundOrderModel
import com.zhengdianfang.atrs.repository.OrderRemoteRepository
import com.zhengdianfang.atrs.repository.dto.OrderRefundResponseDTO
import com.zhengdianfang.atrs.repository.dto.ResponseCode
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class OrderPresenterTest {

    @DelicateCoroutinesApi
    @Test
    fun `should success callback and receive correct model data  when receive successful http response`() {
        val successResponse = OrderRefundResponseDTO("退票成功", ResponseCode.SUCCESS)
        val repository = mockk<OrderRemoteRepository>()
        coEvery { repository.refundOrderRequest(123, "工作计划临时有变") } .returns(successResponse)
        val orderPresenter = OrderPresenter()
        orderPresenter.setTestOrderRemoteRepository(repository)
        orderPresenter.setTestIOProvideDispatcher(Dispatchers.Unconfined)
        orderPresenter.setTestMainProvideDispatcher(Dispatchers.Unconfined)
        val expected = RefundOrderModel("退票成功")
        var actual: RefundOrderModel? = null
        orderPresenter.refundOrder(123, "工作计划临时有变", success = { model -> actual = model }, fail = { model -> actual = model })
        assertEquals(expected, actual)
    }

    @DelicateCoroutinesApi
    @Test
    fun `should failure callback and receive correct model data  when receive failure http response`() {
        val successResponse = OrderRefundResponseDTO("退票失败", ResponseCode.ORDER_EXPIRED_CODE)
        val repository = mockk<OrderRemoteRepository>()
        coEvery { repository.refundOrderRequest(123, "工作计划临时有变") } .returns(successResponse)
        val orderPresenter = OrderPresenter()
        orderPresenter.setTestOrderRemoteRepository(repository)
        orderPresenter.setTestIOProvideDispatcher(Dispatchers.Unconfined)
        orderPresenter.setTestMainProvideDispatcher(Dispatchers.Unconfined)
        val expected = RefundOrderModel("退票失败")
        var actual: RefundOrderModel? = null
        orderPresenter.refundOrder(123, "工作计划临时有变", { model -> actual = model }, { model -> actual = model })
        assertEquals(expected, actual)
    }
}