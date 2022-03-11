package com.zhengdianfang.atrs.ui.order

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.zhengdianfang.atrs.R

class RefundOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_detail_layout)

        val orderViewModel = RefundOrderViewModel(application)

        val refundOrderButton = findViewById<Button>(R.id.refundOrderButton)
        refundOrderButton.setOnClickListener {
            orderViewModel.refundOrder()
        }
       orderViewModel.refundButtonDisabled.observe(this) { disabled ->
           refundOrderButton.visibility = if (disabled) View.INVISIBLE else View.VISIBLE
       }
       findViewById<EditText>(R.id.reasonEditText).addTextChangedListener { text -> orderViewModel.reasonLivData.value = text.toString() }
    }
}