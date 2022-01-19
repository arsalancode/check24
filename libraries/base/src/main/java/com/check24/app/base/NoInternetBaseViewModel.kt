package com.check24.app.base

import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import com.check24.app.core.utils.flow.Event
import java.net.SocketTimeoutException
import com.check24.app.networking.networking.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class NoInternetBaseViewModel @Inject constructor() : ViewModel() {
    val noConnectionEvent: LiveEvent<Pair<Boolean, Throwable?>> = LiveEvent()

    fun updateNoConnectionEvent(status: Boolean, error: Throwable?) {
        noConnectionEvent.postValue(Pair(status, error))
    }

    fun updateNoConnectionEvent(tag: String, status: Boolean, error: Throwable?) {
        noConnectionEvent.postValue(Pair(status, error))
    }

    fun hasInternetConnection(errorEvent: Event.Error): Boolean =
        errorEvent.error !is NoInternetException && errorEvent.error !is SocketTimeoutException
}