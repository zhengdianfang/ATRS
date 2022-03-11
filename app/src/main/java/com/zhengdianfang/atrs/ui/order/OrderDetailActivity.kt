package com.zhengdianfang.atrs.ui.order

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.zhengdianfang.atrs.R

class OrderDetailActivity : AppCompatActivity() {
    private val orderViewModel = OrderViewModel(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_detail_layout)

        val refundOrderButton = findViewById<Button>(R.id.refundOrderButton)
        refundOrderButton.setOnClickListener {
            orderViewModel.refundOrder()
        }
       orderViewModel.refundButtonDisabled.observe(this) { disabled ->
           refundOrderButton.visibility = if (disabled) View.INVISIBLE else View.VISIBLE
       }
    }
}