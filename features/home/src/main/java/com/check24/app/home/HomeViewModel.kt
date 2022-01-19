package com.check24.app.home

import android.util.Log
import androidx.lifecycle.*
import com.check24.app.base.NoInternetBaseViewModel
import com.check24.app.core.utils.flow.Event
import com.check24.app.core.utils.flow.loadingEventFlow
import com.check24.app.home.model.QueryModel
import com.check24.app.home.model.UiStates
import com.check24.app.home.uimodels.ProductUiModel
import com.check24.app.networking.networking.repo.SearchRepository
import com.check24.app.networking.networking.repo.model.Header
import com.check24.app.networking.networking.repo.model.Product
import com.check24.app.networking.networking.repo.model.RepoModel
import com.check24.app.networking.networking.repo.model.SearchResult
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

    lateinit var header: Header
    lateinit var filters: List<String>
    var repoModelList : List<Product>
    val repoUiModelList = MutableLiveData<List<ProductUiModel>>()
    var query = MutableLiveData<QueryModel>()

    private val queryModelFlow = query.asFlow()
        .debounce(500)
        .filter {
            it.query.trim().isEmpty().not()
        }
        .distinctUntilChanged()

    init {
        repoModelList = mutableListOf()
        repoUiModelList.postValue(listOf())

        fetchDataOnQueryChange()
    }

    fun searchRepos( newQuery : String ){
        currentPage = 1
        loadMoreFlag = false
        query.value = QueryModel(newQuery, currentPage)
    }

    fun loadMore(){
        Log.i(TAG, "LoadMore")
        currentPage++
        loadMoreFlag = true
        query.value = QueryModel(query.value?.query!!, currentPage)
    }


    private fun fetchDataOnQueryChange() {

        viewModelScope.launch {

            queryModelFlow.flatMapLatest {
                loadingEventFlow {
                    searchRepository.searchGitRepos(it.query, it.pageNo)
                }
            }.collect {
                handleSearchResultEvent(it)
            }

        }
    }

    private fun handleSearchResultEvent(event: Event<RepoModel>) {

        when(event) {
            is Event.Idle -> {
                Log.i(TAG, "Event:Idle")
            }

            is Event.Loading -> {
                Log.i(TAG, "Event:Loading")
                uiStates.value = if (loadMoreFlag) UiStates.LOAD_MORE else UiStates.LOADING
            }

            is Event.Data -> {
                Log.i(TAG, "Event:Data")

//                if (loadMoreFlag)
//                    repoModelList = repoModelList.plus(event.data.items)
//                else

                repoModelList = event.data.products

                val uiModelList = repoModelList.map {
                    ProductUiModel(it, null)
                }
                repoUiModelList.postValue(uiModelList)

                uiStates.value = if (uiModelList.isEmpty()) UiStates.NO_DATA
                                    else UiStates.SHOW_DATA
            }

            is Event.Error -> {
                Log.e(TAG, "Event:Error")
                uiStates.value = UiStates.ERROR

                if ( event.error is HttpException ){
                    Log.e(TAG, "Message: " + event.error.message)

                } else if (!hasInternetConnection(event)) {
                    updateNoConnectionEvent(true, event.error)

                } else {
                    event.error.printStackTrace()
                }
            }

        }
    }

}