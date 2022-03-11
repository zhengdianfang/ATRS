package com.zhengdianfang.atrs.repository.dto

import java.io.Serializable

data class MakeInvoiceRequestDTO(val companyName: String, val taxID: String, val email: String, val phone: String) : Serializable
