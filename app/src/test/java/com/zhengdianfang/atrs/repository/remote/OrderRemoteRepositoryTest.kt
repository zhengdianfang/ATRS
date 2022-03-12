package com.zhengdianfang.atrs.repository.remote

import com.google.gson.Gson
import com.zhengdianfang.atrs.repository.remote.api.OrderApis
import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceRequestDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class OrderRemoteRepositoryTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var orderRemoteRepository: OrderRemoteRepository
    private val gson = Gson()


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        orderRemoteRepository = OrderRemoteRepository()
        orderRemoteRepository.setOrderApis(
            Retrofit.Builder().baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(OrderApis::class.java)
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should get success response when user refund time before then ticket fly time of air ticket`() {
        val expected = "{\"msg\":\"退票成功\",\"code\":0}"
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(expected))
        runBlocking {
            val actual = orderRemoteRepository.refundOrderRequest(133, "工作计划临时有变")
            assertEquals("/flights-contract/orders/133/refund", mockWebServer.takeRequest().path)
            assertEquals(expected, gson.toJson(actual))
        }
    }

    @Test
    fun `should get failure response when user refund time after then ticket fly time of air ticket`() {
        val expected = "{\"msg\":\"退票失败\",\"code\": -10}"
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody(expected))
        runBlocking {
            try {
                orderRemoteRepository.refundOrderRequest(133, "工作计划临时有变")
                assertEquals("/flights-contract/orders/133/refund", mockWebServer.takeRequest().path)
            } catch (e: HttpException) {
                assertEquals(expected, e.response()?.errorBody()?.string())
            }
        }
    }

    @Test
    fun `should get failure response when user refund ticket and BFF server is error`() {
        val expected = "{\"msg\":\"退票失败\",\"code\": -100}"
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody(expected))
        runBlocking {
            try {
                orderRemoteRepository.refundOrderRequest(133, "工作计划临时有变")
                assertEquals("/flights-contract/orders/133/refund", mockWebServer.takeRequest().path)
            } catch (e: HttpException) {
                assertEquals(expected, e.response()?.errorBody()?.string())
            }
        }
    }

    @Test
    fun `should get success response when user apply make invoice of order and information is correct`() {
        val expected = "{\"msg\":\"开发票成功\",\"code\":0}"
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(expected))
        runBlocking {
            val actual = orderRemoteRepository.makeVoice(133, MakeInvoiceRequestDTO("xxx公司", "12312312312312", "xxxx@gmail.com", "13245432356"))
            assertEquals("/flights-contract/orders/133/invoice", mockWebServer.takeRequest().path)
            assertEquals(expected, gson.toJson(actual))
        }
    }

    @Test
    fun `should get failure response when user apply make invoice of order and information is incorrect`() {
        val expected = "{\"msg\":\"开发票失败\",\"code\":-200}"
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody(expected))
        runBlocking {
            try {
                orderRemoteRepository.makeVoice(133, MakeInvoiceRequestDTO("xxx公司", "wrong tax id", "xxxx@gmail.com", "13245432356"))
                assertEquals("/flights-contract/orders/133/invoice", mockWebServer.takeRequest().path)
            } catch (e: HttpException) {
                assertEquals(expected, e.response()?.errorBody()?.string())
            }
        }
    }

    @Test
    fun `should get failure response when user make invoice and BFF server is error`() {
        val expected = "{\"msg\":\"服务器不可用\",\"code\":-100}"
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody(expected))
        runBlocking {
            try {
                orderRemoteRepository.makeVoice(133, MakeInvoiceRequestDTO("xxx公司", "12312312312312", "xxxx@gmail.com", "13245432356"))
                assertEquals("/flights-contract/orders/133/invoice", mockWebServer.takeRequest().path)
            } catch (e: HttpException) {
                assertEquals(expected, e.response()?.errorBody()?.string())
            }
        }
    }
}