package com.zhengdianfang.atrs.repository.dto

enum class ResponseCode(val code: Int) {
    SUCCESS(0),
    ORDER_EXPIRED_CODE(-10),
    BFF_SERVER_ERROR(-100)
}