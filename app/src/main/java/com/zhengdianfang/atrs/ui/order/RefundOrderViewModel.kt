package com.zhengdianfang.atrs.ui.order

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zhengdianfang.atrs.presenter.OrderPresenter
import kotlinx.coroutines.DelicateCoroutinesApi

class RefundOrderViewModel(application: Application) : AndroidViewModel(application) {
    private val orderPresenter =  OrderPresenter()
    private val orderId = 133L

    val reasonLivData = MutableLiveData<String>()
    val refundButtonDisabled = MutableLiveData<Boolean>(false)

    @DelicateCoroutinesApi
    fun refundOrder() {
       orderPresenter.refundOrder(orderId, reasonLivData.value ?: "", success = { refundOrderModel ->
           Toast.makeText(getApplication(), refundOrderModel.tip, Toast.LENGTH_SHORT).show()
       }, fail = { refundOrderModel ->
           Toast.makeText(getApplication(), refundOrderModel.tip, Toast.LENGTH_SHORT).show()
           refundButtonDisabled.postValue(true)
       })
    }
}