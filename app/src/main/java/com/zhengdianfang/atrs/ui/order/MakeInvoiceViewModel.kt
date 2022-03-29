package com.zhengdianfang.atrs.ui.order

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhengdianfang.atrs.presenter.OrderPresenter
import com.zhengdianfang.atrs.presenter.model.MakeInvoiceInformation
import com.zhengdianfang.atrs.repository.remote.OrderRemoteRepository
import com.zhengdianfang.atrs.services.RetryScheduleService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MakeInvoiceViewModel(private val context: Context) : ViewModel() {
    val companyName = MutableLiveData("")
    val taxId = MutableLiveData("")
    val email = MutableLiveData("")
    val phone = MutableLiveData("")
    val orderId = 133L
    private val orderPresenter = OrderPresenter()
    private val orderRemoteRepository = OrderRemoteRepository()

    fun makeInvoice() {
        viewModelScope.launch {
            orderPresenter.makeInvoice(
                orderId,
                MakeInvoiceInformation(companyName.value!!, taxId.value!!, email.value!!, phone.value!!)
            ).collect {
                Toast.makeText(context, it.tip, Toast.LENGTH_SHORT).show()
            }
        }
    }
}