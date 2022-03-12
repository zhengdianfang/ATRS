package com.zhengdianfang.atrs.presenter.model

import com.zhengdianfang.atrs.repository.remote.dto.MakeInvoiceRequestDTO

data class MakeInvoiceInformation(val companyName: String, val taxID: String, val email: String, val phone: String) {
    fun transformToMakeInvoiceRequestDTO(): MakeInvoiceRequestDTO {
       return MakeInvoiceRequestDTO(companyName, taxID, email, phone)
    }
}