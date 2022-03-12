package com.zhengdianfang.atrs.ui.order

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhengdianfang.atrs.presenter.OrderPresenter
import com.zhengdianfang.atrs.presenter.model.MakeInvoiceInformation
import com.zhengdianfang.atrs.services.RetryScheduleService

class MakeInvoiceViewModel(private val context: Context) : ViewModel() {
    val companyName = MutableLiveData("")
    val taxId = MutableLiveData("")
    val email = MutableLiveData("")
    val phone = MutableLiveData("")
    val orderId = 133L
    private val orderPresenter = OrderPresenter()

    fun makeInvoice() {
       orderPresenter.makeInvoice(orderId, MakeInvoiceInformation(companyName.value!!, taxId.value!!, email.value!!, phone.value!!), { result ->
           Toast.makeText(context, result.tip, Toast.LENGTH_SHORT).show()
       }, { result ->
           Toast.makeText(context, result.tip, Toast.LENGTH_SHORT).show()
       },{ retryId -> RetryScheduleService.start(retryId)})
    }
}