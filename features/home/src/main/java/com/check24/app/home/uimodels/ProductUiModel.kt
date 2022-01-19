package com.check24.app.home.uimodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.check24.app.home.R
import com.check24.app.core.lists.DataBoundModel
import com.check24.app.core.utils.toDate
import com.check24.app.core.utils.toPrice
import com.check24.app.networking.networking.repo.model.Product

class ProductUiModel(product: Product, private val onClick: (() -> Unit)?) :
    DataBoundModel(R.layout.repo_row) {

    init {
        Log.i("RepoUiModel", product.toString())
    }

    val imageUrl = MutableLiveData<String>().apply { postValue(product.imageURL) }
    val name = MutableLiveData<String>().apply { postValue(product.name) }
    val date = MutableLiveData<String>().apply { postValue(product.releaseDate.toDate()) }
    val shortDesc = MutableLiveData<String>().apply { postValue(product.description) }
    val rating = MutableLiveData<Float>().apply { postValue(product.rating) }
    val price = MutableLiveData<String>().apply { postValue(product.price.value.toPrice(product.price.currency)) }

    fun onClick(clickedView: View) {
        onClick?.invoke()
    }

}
