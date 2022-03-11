package com.zhengdianfang.atrs.ui.order

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.zhengdianfang.atrs.R

class MakeInvoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.make_invoice_layout)

        val makeInvoiceViewModel = MakeInvoiceViewModel(applicationContext)
        findViewById<EditText>(R.id.companyNameEdit).addTextChangedListener {
            makeInvoiceViewModel.companyName.value = it.toString()
        }
        findViewById<EditText>(R.id.taxIdEdit).addTextChangedListener {
            makeInvoiceViewModel.taxId.value = it.toString()
        }
        findViewById<EditText>(R.id.emailEdit).addTextChangedListener {
            makeInvoiceViewModel.email.value = it.toString()
        }
        findViewById<EditText>(R.id.phoneEdit).addTextChangedListener {
            makeInvoiceViewModel.phone.value = it.toString()
        }
        findViewById<Button>(R.id.makeInvoiceButton).setOnClickListener {
           makeInvoiceViewModel.makeInvoice()
        }
    }
}