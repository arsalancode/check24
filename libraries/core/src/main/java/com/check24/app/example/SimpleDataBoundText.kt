package com.check24.app.example

import androidx.lifecycle.MutableLiveData
import com.github.repos.core.R
import com.check24.app.core.lists.DataBoundModel

class SimpleDataBoundText : DataBoundModel(R.layout.data_bound_text) {
    val text = MutableLiveData<String>()
}