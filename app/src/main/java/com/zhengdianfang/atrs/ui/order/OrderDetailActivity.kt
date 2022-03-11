package com.zhengdianfang.atrs.ui.order

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.zhengdianfang.atrs.R

class OrderDetailActivity : AppCompatActivity() {
    private val orderViewModel = OrderViewModel(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_detail_layout)

        findViewById<Button>(R.id.refundOrderButton).setOnClickListener {
            orderViewModel.refundOrder()
        }
    }
}