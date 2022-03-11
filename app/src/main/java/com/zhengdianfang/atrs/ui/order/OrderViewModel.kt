package com.zhengdianfang.atrs.ui.order

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zhengdianfang.atrs.presenter.OrderPresenter

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val orderPresenter =  OrderPresenter()
    private val orderId = 123L

    private val reasonLivData = MutableLiveData<String>()

    fun refundOrder() {
       orderPresenter.refundOrder(orderId, reasonLivData.value ?: "", success = { refundOrderModel ->
           Toast.makeText(getApplication(), refundOrderModel.tip, Toast.LENGTH_SHORT).show()
       })
    }
}