package com.check24.app.home.uimodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.check24.app.core.lists.DataBoundModel
import com.check24.app.home.R

class FooterUiModel(private val onClick: (() -> Unit)?) :
    DataBoundModel(R.layout.footer_row) {

    fun onClick(clickedView: View) {
        onClick?.invoke()
    }

}
