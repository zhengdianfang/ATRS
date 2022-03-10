package com.zhengdianfang.atrs.presenter

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.TestOnly

open abstract class BasePresenter {
    var ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    var mainDispatcher: CoroutineDispatcher = Dispatchers.IO

    @TestOnly
    fun setTestIOProvideDispatcher(testDispatcher: CoroutineDispatcher) {
       this.ioDispatcher = testDispatcher
    }

    @TestOnly
    fun setTestMainProvideDispatcher(testDispatcher: CoroutineDispatcher) {
        this.mainDispatcher = testDispatcher
    }
}