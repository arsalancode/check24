package com.check24.app.networking.networking.repo

import com.check24.app.core.utils.flow.asFlow
import com.check24.app.networking.domain.InternetHelper
import com.check24.app.networking.networking.repo.model.RepoModel
import com.check24.app.networking.networking.repo.model.SearchResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class SearchRepositoryImpl @Inject constructor (
    val internetHelper: InternetHelper,
    val service: RepoService
) : SearchRepository {

    @FlowPreview
    override suspend fun searchGitRepos(query: String, pageNo: Int): Flow<SearchResult> =
        internetHelper.executeCheckInternetStatus()
            .flatMapConcat { asFlow { service.searchRepos(query, pageNo) } }
            .map { it }


}