package com.check24.app.networking.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.check24.app.core.utils.flow.Event
import com.check24.app.core.utils.flow.loadingEventFlow
import com.check24.app.networking.domain.InternetHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoConnectionViewModel @Inject constructor(
    private val helper: InternetHelper
) : ViewModel() {

    val loading: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val hasInternetConnection: LiveEvent<Boolean> = LiveEvent()

    fun onClickRetry() {
        isInternetAvailable()
    }

    private fun isInternetAvailable() {
        viewModelScope.launch {
            loadingEventFlow {
                helper.executeCheckInternetStatus()
            }.collect { handleInternetStatus(it) }
        }
    }

    private fun handleInternetStatus(event: Event<Unit>) {
        when (event) {
            is Event.Loading -> {
                loading.value = View.VISIBLE
            }
            is Event.Data -> {
                loading.value = View.GONE
                hasInternetConnection.value = true
            }
            is Event.Error -> {
                loading.value = View.GONE
            }
        }
    }
}