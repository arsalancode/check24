package com.check24.app.home.uimodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.check24.app.home.R
import com.check24.app.core.lists.DataBoundModel
import com.check24.app.networking.networking.repo.model.Product

class ProductUiModel(product: Product, private val onClick: (() -> Unit)?) :
    DataBoundModel(R.layout.repo_row) {

    init {
        Log.i("RepoUiModel", product.toString())
    }

    val ownerAvatarUrl = MutableLiveData<String>().apply { postValue(product.imageURL) }
    val ownerName = MutableLiveData<String>().apply { postValue(product.name) }
    val repoName = MutableLiveData<String>().apply { postValue(product.name) }
    val repoTitle = MutableLiveData<String>().apply { postValue(product.description) }
    val repoDesc = MutableLiveData<String>().apply { postValue(product.longDescription) }
    val repoUrl = MutableLiveData<String>().apply { postValue(product.price.value.toString()) }

    fun onClick(clickedView: View) {
        onClick?.invoke()
    }

}
