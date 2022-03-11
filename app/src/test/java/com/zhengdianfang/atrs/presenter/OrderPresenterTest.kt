package com.zhengdianfang.atrs.presenter

import com.zhengdianfang.atrs.presenter.model.MakeInvoiceInformation
import com.zhengdianfang.atrs.presenter.model.MakeInvoiceResultModel
import com.zhengdianfang.atrs.presenter.model.RefundOrderResultModel
import com.zhengdianfang.atrs.repository.OrderRemoteRepository
import com.zhengdianfang.atrs.repository.dto.MakeInvoiceRequestDTO
import com.zhengdianfang.atrs.repository.dto.MakeInvoiceResponseDTO
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
    fun `should success callback and receive correct model data  when user refund time before then ticket fly time of air ticket`() {
        val successResponse = OrderRefundResponseDTO("退票成功", ResponseCode.SUCCESS)
        val repository = mockk<OrderRemoteRepository>()
        coEvery { repository.refundOrderRequest(133, "工作计划临时有变") }.returns(successResponse)
        val orderPresenter = OrderPresenter()
        orderPresenter.setTestOrderRemoteRepository(repository)
        orderPresenter.setTestIOProvideDispatcher(Dispatchers.Unconfined)
        orderPresenter.setTestMainProvideDispatcher(Dispatchers.Unconfined)
        val expected = RefundOrderResultModel("退票成功")
        var actual: RefundOrderResultModel? = null
        orderPresenter.refundOrder(
            133,
            "工作计划临时有变",
            success = { model -> actual = model },
            fail = {})
        assertEquals(expected, actual)
    }

    @DelicateCoroutinesApi
    @Test
    fun `should failure callback and receive correct model data when user refund time after then ticket fly time of air ticket`() {
        val successResponse = OrderRefundResponseDTO("退票失败", ResponseCode.ORDER_EXPIRED_CODE)
        val repository = mockk<OrderRemoteRepository>()
        coEvery { repository.refundOrderRequest(133, "工作计划临时有变") }.returns(successResponse)
        val orderPresenter = OrderPresenter()
        orderPresenter.setTestOrderRemoteRepository(repository)
        orderPresenter.setTestIOProvideDispatcher(Dispatchers.Unconfined)
        orderPresenter.setTestMainProvideDispatcher(Dispatchers.Unconfined)
        val expected = RefundOrderResultModel("退票失败")
        var actual: RefundOrderResultModel? = null
        orderPresenter.refundOrder(
            133,
            "工作计划临时有变",
            {},
            {model -> actual = model})
        assertEquals(expected, actual)
    }

    @DelicateCoroutinesApi
    @Test
    fun `should failure callback and receive correct model data when BFF server is error of refund ticket api`() {
        val successResponse = OrderRefundResponseDTO("退票失败", ResponseCode.BFF_SERVER_ERROR)
        val repository = mockk<OrderRemoteRepository>()
        coEvery { repository.refundOrderRequest(133, "工作计划临时有变") }.returns(successResponse)
        val orderPresenter = OrderPresenter()
        orderPresenter.setTestOrderRemoteRepository(repository)
        orderPresenter.setTestIOProvideDispatcher(Dispatchers.Unconfined)
        orderPresenter.setTestMainProvideDispatcher(Dispatchers.Unconfined)
        val expected = RefundOrderResultModel("退票失败")
        var actual: RefundOrderResultModel? = null
        orderPresenter.refundOrder(
            133,
            "工作计划临时有变",
            {},
            { model -> actual = model })
        assertEquals(expected, actual)
    }

    @DelicateCoroutinesApi
    @Test
    fun `should success callback and receive correct model data  when make invoice successful`() {
        val successResponse = MakeInvoiceResponseDTO("开发票成功", ResponseCode.SUCCESS)
        val repository = mockk<OrderRemoteRepository>()
        coEvery {
            repository.makeVoice(
                133,
                MakeInvoiceRequestDTO("xxx公司", "12312312312312", "xxxx@gmail.com", "13245432356")
            )
        }.returns(successResponse)
        val orderPresenter = OrderPresenter()
        orderPresenter.setTestOrderRemoteRepository(repository)
        orderPresenter.setTestIOProvideDispatcher(Dispatchers.Unconfined)
        orderPresenter.setTestMainProvideDispatcher(Dispatchers.Unconfined)
        val expected = MakeInvoiceResultModel("开发票成功")
        var actual: MakeInvoiceResultModel? = null
        orderPresenter.makeInvoice(
            133,
            MakeInvoiceInformation("xxx公司", "12312312312312", "xxxx@gmail.com", "13245432356"),
         { resultModel -> actual = resultModel }, {})
        assertEquals(expected, actual)
    }

    @DelicateCoroutinesApi
    @Test
    fun `should failure callback and receive correct model data when user input incorrect taxId`() {
        val successResponse = MakeInvoiceResponseDTO("开发票失败", ResponseCode.TAX_ID_NOT_EXIST)
        val repository = mockk<OrderRemoteRepository>()
        coEvery {
            repository.makeVoice(
                133,
                MakeInvoiceRequestDTO("xxx公司", "wrong tax-id", "xxxx@gmail.com", "13245432356")
            )
        }.returns(successResponse)
        val orderPresenter = OrderPresenter()
        orderPresenter.setTestOrderRemoteRepository(repository)
        orderPresenter.setTestIOProvideDispatcher(Dispatchers.Unconfined)
        orderPresenter.setTestMainProvideDispatcher(Dispatchers.Unconfined)
        val expected = MakeInvoiceResultModel("开发票失败")
        var actual: MakeInvoiceResultModel? = null
        orderPresenter.makeInvoice(
            133,
            MakeInvoiceInformation("xxx公司", "wrong tax-id", "xxxx@gmail.com", "13245432356"), {}, { resultModel -> actual = resultModel })
        assertEquals(expected, actual)

    }
}