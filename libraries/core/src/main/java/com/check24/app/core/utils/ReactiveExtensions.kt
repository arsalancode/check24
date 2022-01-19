package com.check24.app.core.utils

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Boolean>.show() = this.postValue(true)

fun MutableLiveData<Boolean>.hide() = this.postValue(false)
