package com.check24.app.home

import android.util.Log
import androidx.lifecycle.*
import com.check24.app.base.NoInternetBaseViewModel
import com.check24.app.core.lists.DataBoundModel
import com.check24.app.core.utils.flow.Event
import com.check24.app.core.utils.flow.asFlow
import com.check24.app.core.utils.flow.loadingEventFlow
import com.check24.app.core.utils.toDate
import com.check24.app.core.utils.toPrice
import com.check24.app.home.model.UiStates
import com.check24.app.home.uimodels.FooterUiModel
import com.check24.app.home.uimodels.ProductUiModel
import com.check24.app.networking.networking.repo.SearchRepository
import com.check24.app.networking.networking.repo.model.Header
import com.check24.app.networking.networking.repo.model.Price
import com.check24.app.networking.networking.repo.model.Product
import com.check24.app.networking.networking.repo.model.RepoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : NoInternetBaseViewModel() {

    private val TAG = "HomeViewModel"

    var currentPage = 1
    var loadMoreFlag = false
    val uiStates = MutableLiveData(UiStates.WELCOME)
    val onClick = MutableLiveData<Boolean>()

//    var selectedProduct : Product = getProduct()
    var selectedProduct : MutableLiveData<Product> = MutableLiveData()

    var header = MutableLiveData<Header>()
    var filters: List<String> = listOf()
    var repoModelList: List<Product>
    val repoUiModelList = MutableLiveData<List<DataBoundModel>>()

    init {
        repoModelList = mutableListOf()
        repoUiModelList.postValue(listOf())

        fetchProducts()
    }

    fun updateList( item: Int ){
        val list = when (item){
            1 -> {
                repoModelList.filter {
                    it.available
                }
            }
            2 -> {
                repoModelList.filter {
                    it.isFavorite
                }
            }
            else -> {
                repoModelList
            }
        }

        val uiModelList : MutableList<DataBoundModel> = list.map {
            ProductUiModel(
                it,
                if (it.available) R.layout.product_row else R.layout.product_row_inverse
            ) {
                // click listener
                Log.i(TAG, "Selected Product: $it")
                selectedProduct.value = it

                // now move to detailed fragment
                onClick.postValue(true)
            }
        }.toMutableList()

        uiModelList.add(FooterUiModel(null))

        repoUiModelList.postValue(uiModelList)

    }


    private fun fetchProducts() {

        viewModelScope.launch {

            asFlow {
                loadingEventFlow {
                    searchRepository.fetchProducts()
                }
            }
            .flatMapLatest {
                it
            }
            .collect {
                    handleProductsResultEvent(it)
            }

        }
    }

    private fun handleProductsResultEvent(event: Event<RepoModel>) {

        when (event) {
            is Event.Idle -> {
                Log.i(TAG, "Event:Idle")
            }

            is Event.Loading -> {
                Log.i(TAG, "Event:Loading")
                uiStates.value = if (loadMoreFlag) UiStates.LOAD_MORE else UiStates.LOADING
            }

            is Event.Data -> {
                Log.i(TAG, "Event:Data")

                header.postValue(event.data.header)
                repoModelList = event.data.products

                val uiModelList: MutableList<DataBoundModel> = repoModelList.map {
                    ProductUiModel(
                        it,
                        if (it.available) R.layout.product_row else R.layout.product_row_inverse
                    ) {
                        // click listener
                        Log.i(TAG, "Selected Product: $it")
                        //selectedProduct.postValue(it)
                        //selectedProduct = it
                        //selectedProduct.postValue(it)
                        selectedProduct.value = it

                        // now move to detailed fragment
                        onClick.postValue(true)
                    }
                }.toMutableList()

                uiModelList.add(FooterUiModel(null))
                repoUiModelList.postValue(uiModelList)
                //selectedProduct = repoModelList[0]// dummy


                uiStates.value = if (uiModelList.isEmpty()) UiStates.NO_DATA
                else UiStates.SHOW_DATA
            }

            is Event.Error -> {
                Log.e(TAG, "Event:Error")
                uiStates.value = UiStates.ERROR

                if (event.error is HttpException) {
                    Log.e(TAG, "Message: " + event.error.message)

                } else if (!hasInternetConnection(event)) {
                    updateNoConnectionEvent(true, event.error)

                } else {
                    event.error.printStackTrace()
                }
            }

        }
    }

//    fun getDate(): String = selectedProduct.value!!.releaseDate.toDate()
//    fun getPrice() : String = selectedProduct.value!!.price.value.toPrice(selectedProduct.value!!.price.currency)

    fun getDate(): String {

        if ( selectedProduct.value != null ){
            return selectedProduct.value!!.releaseDate.toDate()
        }

        return ""
    }

    fun getPrice() : String {

        if ( selectedProduct.value == null )
            return ""

        return selectedProduct.value!!.price.value.toPrice(selectedProduct.value!!.price.currency)
    }


    private fun getProduct() = Product(
        0,
        "test",
        "---",
        1459629605,
        "short desc",
        "long desc",
        3.4F,
        Price(0F, "EUR"),
        false,
        false
    )

}